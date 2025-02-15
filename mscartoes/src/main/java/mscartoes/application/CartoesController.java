package mscartoes.application;

import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import mscartoes.application.dto.CartaoCreateDto;
import mscartoes.application.service.CartaoService;
import mscartoes.domain.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService service;

    @GetMapping
    public String status() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoCreateDto cartaoDto, ServletRequest servletRequest){
        Cartao cartao = cartaoDto.toCartao();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = service.getCartaoRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }
}
