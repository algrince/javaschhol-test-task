package com.algrince.finaltask.utils;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ClientValidator implements Validator {

    private final ClientsService clientsService;

    @Autowired
    public ClientValidator(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        Optional<Client> clientFromDB = clientsService.loadUserByEmail(client.getEmail());

        if (clientFromDB.isPresent()) {
            errors.rejectValue("email", "The user with this email already exists");
        }
    }
}
