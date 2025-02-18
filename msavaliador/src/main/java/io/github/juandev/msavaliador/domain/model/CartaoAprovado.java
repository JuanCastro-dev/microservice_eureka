package io.github.juandev.msavaliador.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {
    private String nome;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
