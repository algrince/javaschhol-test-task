package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.repositories.ClientsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }
}
