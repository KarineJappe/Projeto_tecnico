package com.projetoTecnico.projetoTecnico.controller;

import  com.projetoTecnico.projetoTecnico.model.Cliente;
import com.projetoTecnico.projetoTecnico.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository repository;

    ClienteController(ClienteRepository clienteRepository) {
        this.repository = clienteRepository;
    }
    // abaixo segue o CRUD da aplicação.

    @GetMapping
    public List<Cliente> findAll(){
        return repository.findAll();
    }
    //listar cliente pelo id
    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable Long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
//    criar novo cliente
    @Transactional
    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) throws Exception {
        Cliente clienteCpfCnpj = repository.findClienteByCpfCnpjIgnoreCase(cliente.getCpfCnpj());
        if (Objects.nonNull(clienteCpfCnpj)){
            throw new Exception("Cpf ou Cnpj já existente!");
        }
        cliente.setTelefone(cliente.getTelefone());
        cliente.setEndereco(cliente.getEndereco());
        return repository.save(cliente);
    }

//    editando
    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Cliente cliente) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(cliente.getNome());
                    record.setCpfCnpj(cliente.getCpfCnpj());

                    Cliente updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}