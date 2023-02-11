package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.repositories.ClientsRepository;
import com.algrince.finaltask.security.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientDetailsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = clientsRepository.findByEmail(email);

        if (client.isEmpty())
            throw new UsernameNotFoundException("Account not found");

        return new ClientDetails(client.get());
    }
}
