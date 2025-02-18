package io.github.juandev.msavaliador.application;

import io.github.juandev.msavaliador.application.exception.DadosClienteNotFoundException;
import io.github.juandev.msavaliador.application.exception.ErroComunicacaoMicroservicesException;
import io.github.juandev.msavaliador.application.service.AvaliadorService;
import io.github.juandev.msavaliador.domain.model.*;
import io.github.juandev.msavaliador.infre.clients.CartoesControllerClient;
import io.github.juandev.msavaliador.infre.clients.ClienteControllerClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{avaliacoes-credito}")
@RequiredArgsConstructor
public class AvaliadorController {

    private final AvaliadorService service;
    private final AvaliadorService avaliadorService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam ("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        }
        catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao){
        try {
            RetornoAvaliacaoCliente retorno = avaliadorService.realizarAvaliacaoCliente(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda());
            return ResponseEntity.ok(retorno);
        }
        catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
        try{
            ProtocoloSolicitacaoCartao protocolo = avaliadorService.solicitarEmissaoDeCartao(dados);
            return ResponseEntity.ok(protocolo);
        }
        catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
