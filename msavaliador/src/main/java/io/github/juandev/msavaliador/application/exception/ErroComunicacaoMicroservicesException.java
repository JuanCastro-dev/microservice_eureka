package io.github.juandev.msavaliador.application.exception;

import lombok.Getter;

@Getter
public class ErroComunicacaoMicroservicesException extends RuntimeException {

    private Integer status;

    public ErroComunicacaoMicroservicesException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
