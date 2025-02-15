package mscartoes.application.dto;

import lombok.Data;
import mscartoes.domain.BandeiraCartao;
import mscartoes.domain.Cartao;

import java.math.BigDecimal;

@Data
public class CartaoCreateDto {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toCartao() {
        return new Cartao(nome,bandeira,renda,limite);
    }
}
