package io.github.JuanCastro_dev.msclientes.application;

import io.github.JuanCastro_dev.msclientes.application.dto.ClienteCreateDto;
import io.github.JuanCastro_dev.msclientes.application.service.ClienteService;
import io.github.JuanCastro_dev.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClienteService service;

    @GetMapping
    private String status(){
        return "OK";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteCreateDto clienteDto){
        Cliente cliente = clienteDto.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Optional<Cliente>> dadosCliente(@RequestParam String cpf){
        var cliente = service.getByCpf(cpf);
        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
