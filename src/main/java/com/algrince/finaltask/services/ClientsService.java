package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.repositories.ClientsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientsService {

    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository, PasswordEncoder passwordEncoder) {
        this.clientsRepository = clientsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    public Optional<Client> loadUserByEmail(String email) {
        Optional<Client> client = clientsRepository.findByEmail(email);
        return client;
    }

    public void register(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientsRepository.save(client);
    }
}
