package com.funcionarioservice.domain.services;

import com.funcionarioservice.domain.exceptions.departamento.DepartamentoIdIllegalArgumentException;
import com.funcionarioservice.domain.exceptions.funcionario.FuncionarioAlreadyExistsException;
import com.funcionarioservice.domain.exceptions.funcionario.FuncionarioNotFoundException;
import com.funcionarioservice.domain.mapper.DepartamentoMapper;
import com.funcionarioservice.domain.model.Departamentos;
import com.funcionarioservice.domain.model.Funcionarios;
import com.funcionarioservice.domain.repository.FuncionarioRepository;
import com.funcionarioservice.domain.services.contract.FuncionarioContract;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceContract implements FuncionarioContract {

    private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoServiceContract departamentoServiceContract;


    @Override
    public FuncionariosResponse findById(Integer matricula) {

        return funcionarioRepository.findByMatricula(matricula)
                .map(FuncionariosResponse::of)
                .orElseThrow(() -> new FuncionarioNotFoundException(matricula));
    }

    @Override
    public FuncionariosResponse save(Funcionarios funcionario) {

        if (funcionarioRepository.existsByEmail(funcionario.getEmail())) {
            throw new FuncionarioAlreadyExistsException(funcionario.getEmail());
        }

        if (funcionario.getDepartamento().getId() == null) {
            throw new DepartamentoIdIllegalArgumentException("O ID do departamento não pode ser nulo.");
        }

        var departamento = departamentoServiceContract.findById(funcionario.getDepartamento().getId());
        funcionario.setDepartamento(DepartamentoMapper.dtoResponseToEntity(departamento));

        return FuncionariosResponse.of(funcionarioRepository.save(funcionario));
    }

    @Override
    public FuncionariosResponse update(Integer matricula, @Valid Funcionarios funcionario) {
        departamentoServiceContract.findById(funcionario.getDepartamento().getId());

        return funcionarioRepository.findByMatricula(matricula)
                .map(existingFuncionario -> {
                    var funcUpdate = new Funcionarios(existingFuncionario.getMatricula(),
                            funcionario.getNome(),
                            funcionario.getSobrenome(),
                            funcionario.getCargo(),
                            funcionario.getSalario(),
                            funcionario.getCargaDiaria(),
                            funcionario.getCargaSemanal(),
                            funcionario.getCargaMensal(),
                            funcionario.getTipoContrato(),
                            funcionario.getDataEntrada(),
                            funcionario.getDepartamento(),
                            existingFuncionario.getCreatedDate(),
                            existingFuncionario.getLastModifiedDate(),
                            existingFuncionario.getVersion());
                    return FuncionariosResponse.of(funcionarioRepository.save(funcUpdate));
                }).orElseGet(() -> save(funcionario));

    }

    @Override
    public void delete(Integer matricula) {

        Funcionarios funcionarios = funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new FuncionarioNotFoundException(matricula));

        funcionarios.getDepartamento().getFuncionario().remove(funcionarios);
        funcionarioRepository.delete(funcionarios);
    }

    @Override
    public List<FuncionariosResponse> findAll() {

        return StreamSupport.stream(funcionarioRepository.findAll().spliterator(), false)
                .map(FuncionariosResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public Page<FuncionariosResponse> findAll(int page, int size) {

        Pageable paginacao = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        Page<Funcionarios> funcionariosPage = funcionarioRepository.findAll(paginacao);

        List<FuncionariosResponse> listFuncionarioResponse = funcionariosPage.getContent()
                .stream()
                .map(FuncionariosResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(listFuncionarioResponse, paginacao, funcionariosPage.getTotalElements());
    }

}
