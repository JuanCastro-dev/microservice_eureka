package io.github.juandev.msavaliador.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mqfig {

    private String emissaoCartoesFila;

    @Value("${mq.queues.emissao-cartoes}")
    public Queue queueEmissaoCartoes(){
        return new Queue(emissaoCartoesFila,true);
    }
}
