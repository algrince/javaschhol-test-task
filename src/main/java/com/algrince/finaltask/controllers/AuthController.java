package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.UserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserValidator userValidator;
    private final UsersService usersService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, String> makeLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(), authenticationDTO.getPassword());

        // Try login using authentication provider
        // Move logic
        try {
           authenticationManager.authenticate(authenticationInputToken);
        } catch (BadCredentialsException e) {
            log.info("Incorrect credentials given by the user");
            // Response.entity
            return Map.of("message", "Incorrect credentials");
        }

        log.info("The user has correct credentials, proceeding to token generation");

        // If login is successful, generate new token
        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
        return Map.of("jwt-token", token);
    }


    @PostMapping("/registration")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, String> makeRegistration(@RequestBody @Valid UserDTO userDTO,
                                   BindingResult bindingResult) {
        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);


        if (bindingResult.hasErrors()) {
            log.warn("There was a problem during user validation");
            return Map.of("message", "error");
        }
        log.info("The user has been validated successfully");
        usersService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return Map.of("jwt-token", token);
    }

    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
