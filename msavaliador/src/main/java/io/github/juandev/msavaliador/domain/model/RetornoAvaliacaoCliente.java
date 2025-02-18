package io.github.juandev.msavaliador.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class RetornoAvaliacaoCliente {

    public List<CartaoAprovado> cartoes;
}
