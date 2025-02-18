package io.github.juandev.msavaliador.application.service;

import feign.FeignException;
import io.github.juandev.msavaliador.application.exception.DadosClienteNotFoundException;
import io.github.juandev.msavaliador.application.exception.ErroComunicacaoMicroservicesException;
import io.github.juandev.msavaliador.application.exception.ErroSolicitacaoDeCartaoException;
import io.github.juandev.msavaliador.domain.model.*;
import io.github.juandev.msavaliador.infre.clients.CartoesControllerClient;
import io.github.juandev.msavaliador.infre.clients.ClienteControllerClient;
import io.github.juandev.msavaliador.infre.mqueue.EmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private ClienteControllerClient clienteClient;
    private CartoesControllerClient cartaoCliente;
    private EmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente (String cpf) throws DadosClienteNotFoundException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartaoCliente.getCartoesByCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }
        catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.SC_NOT_FOUND == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(),e.status());
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacaoCliente (String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartaoCliente.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            cartoes.stream().map(cartao ->
            {
                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal rendaBD = BigDecimal.valueOf(renda);
                BigDecimal idade = BigDecimal.valueOf(dadosClienteResponse.getBody().getIdade()));

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setNome(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(cartao.getLimiteBasico());
            }).collect(Collectors.toList());
        }
        catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.SC_NOT_FOUND == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(),e.status());
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoDeCartao(DadosSolicitacaoEmissaoCartao dados){
        try{
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }
        catch (Exception e){
            throw new ErroSolicitacaoDeCartaoException(e.getMessage());
        }

    }

}
