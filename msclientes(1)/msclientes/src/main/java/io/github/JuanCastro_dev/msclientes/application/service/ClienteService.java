package io.github.JuanCastro_dev.msclientes.application.service;

import io.github.JuanCastro_dev.msclientes.domain.Cliente;
import io.github.JuanCastro_dev.msclientes.infra.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

}
