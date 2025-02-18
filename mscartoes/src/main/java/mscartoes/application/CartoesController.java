package mscartoes.application;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import mscartoes.application.dto.CartaoCreateDto;
import mscartoes.application.dto.ClienteCartaoDto;
import mscartoes.application.service.CartaoService;
import mscartoes.application.service.ClienteCartaoService;
import mscartoes.domain.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoCreateDto cartaoDto, ServletRequest servletRequest){
        Cartao cartao = cartaoDto.toCartao();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartaoRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoDto>> getCartoesByCliente(@RequestParam String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listByCpf(cpf);
        List<ClienteCartaoDto> resultList = lista.stream().map(ClienteCartaoDto::toClienteCartaoDto).collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
