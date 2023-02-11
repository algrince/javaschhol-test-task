package com.algrince.finaltask.controllers;

import com.algrince.finaltask.models.Client;
import com.algrince.finaltask.services.RegistrationService;
import com.algrince.finaltask.utils.ClientValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final ClientValidator clientValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(ClientValidator clientValidator, RegistrationService registrationService) {
        this.clientValidator = clientValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("client") Client client) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String makeRegistration(@ModelAttribute("client") @Valid Client client,
                                   BindingResult bindingResult) {
        clientValidator.validate(client, bindingResult);
        registrationService.register(client);

        return "redirect:/login";
    }
}
