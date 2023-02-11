package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    public final ClientsRepository clientsRepository;

    @Autowired
    public RegistrationService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Transactional
    public void register(Client client) {
        clientsRepository.save(client);
    }
}
