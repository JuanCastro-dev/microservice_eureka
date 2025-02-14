package io.github.JuanCastro_dev.msclientes.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @GetMapping
    private String status(){
        return "OK";
    }
}
