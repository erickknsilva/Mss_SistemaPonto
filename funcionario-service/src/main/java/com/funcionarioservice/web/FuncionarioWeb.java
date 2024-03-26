package com.funcionarioservice.web;

import com.funcionarioservice.domain.mapper.FuncionarioMapper;
import com.funcionarioservice.domain.model.Funcionarios;
import com.funcionarioservice.domain.services.FuncionarioServiceContract;
import com.funcionarioservice.domain.services.contract.FuncionarioContract;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosRequest;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/funcionarios")
public class FuncionarioWeb {

    private final FuncionarioContract funcionarioContract;

    public FuncionarioWeb(FuncionarioServiceContract funcionarioService) {
        this.funcionarioContract = funcionarioService;
    }


    @GetMapping("{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionariosResponse findById(@PathVariable Integer matricula) {

        return this.funcionarioContract.findById(matricula);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FuncionariosResponse> save(@Valid @RequestBody FuncionariosRequest request) {

        var funcionarios = this.funcionarioContract.save(FuncionarioMapper.fromDtoToEntity(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matricula}")
                .buildAndExpand(funcionarios.matricula()).toUri();

        return ResponseEntity.created(uri).body(funcionarios);
    }


    @PutMapping("/update/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FuncionariosResponse> update(@PathVariable Integer matricula,
                                                       @Valid @RequestBody Funcionarios funcionario) {

        var funcUpdate = this.funcionarioContract.update(matricula, funcionario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matricula}")
                .buildAndExpand(funcUpdate.matricula()).toUri();
        return ResponseEntity.created(uri).body(funcUpdate);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionariosResponse> findAll() {

        return this.funcionarioContract.findAll();
    }

    @DeleteMapping("/delete/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer matricula) {

        this.funcionarioContract.delete(matricula);
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<FuncionariosResponse> findAll(@PathVariable int page,
                                              @PathVariable int size) {

        return this.funcionarioContract.findAll(page, size);
    }

}
