package io.github.juandev.msavaliador.infre.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.juandev.msavaliador.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
public class EmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartao;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        var json = convertIntoJson(dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartao.getName(), json);
    }

    private String convertIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }

}
