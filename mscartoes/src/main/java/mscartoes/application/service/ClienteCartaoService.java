package mscartoes.application.service;

import lombok.RequiredArgsConstructor;
import mscartoes.application.ClienteCartao;
import mscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
