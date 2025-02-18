package io.github.juandev.msavaliador.infre.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteControllerClient {

    @GetMapping
    String status();

    @GetMapping(params = "cpf")
    ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){

    }

}
