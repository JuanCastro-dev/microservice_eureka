package io.github.JuanCastro_dev.msclientes.application.dto;

import io.github.JuanCastro_dev.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteCreateDto {

    private String cpf;
    private String nome;
    private int idade;

    public Cliente toModel() {
        return new Cliente(nome,idade,cpf);
    }
}
