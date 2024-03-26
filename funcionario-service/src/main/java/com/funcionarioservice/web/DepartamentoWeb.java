package com.funcionarioservice.web;


import com.funcionarioservice.domain.mapper.DepartamentoMapper;
import com.funcionarioservice.domain.services.contract.DepartamentoContract;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentoRequest;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentosResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/departamentos")
public class DepartamentoWeb {

    private final DepartamentoContract departamentoContract;

    public DepartamentoWeb(DepartamentoContract departamentoService) {
        this.departamentoContract = departamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DepartamentosResponse> create(@Valid @RequestBody DepartamentoRequest departamentos) {

        var departamento = departamentoContract.save(DepartamentoMapper.fromDtoToEntity(departamentos));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(departamento.id()).toUri();

        return ResponseEntity.created(uri).body(departamento);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DepartamentosResponse> update(@PathVariable("id") Integer id, @Valid @RequestBody DepartamentoRequest request) {
        var depUpdate = departamentoContract.update(id, DepartamentoMapper.fromDtoToEntity(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(depUpdate.id()).toUri();
        return ResponseEntity.created(uri).body(depUpdate);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartamentosResponse findById(@PathVariable("id") Integer id) {
        return departamentoContract.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        departamentoContract.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentosResponse> findAll() {

        return departamentoContract.findAll();
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<DepartamentosResponse> findAllPage(@PathVariable int page, @PathVariable int size) {

        return departamentoContract.findAll(page, size);
    }


}
