package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.LoggedUserDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.security.JwtResponseEntity;
import com.algrince.finaltask.services.AuthService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.utils.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsersService usersService;
    private final DTOMapper dtoMapper;

    @PostMapping("login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> makeLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        String token;
        try {
            token = authService.login(authenticationDTO);
        } catch (BadCredentialsException e) {
            log.info("Incorrect credentials given by the user");
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
        }

        User foundUser = usersService.findByEmail(authenticationDTO.getEmail());
        LoggedUserDTO foundUserDTO = dtoMapper.mapClass(foundUser, LoggedUserDTO.class);
        return new JwtResponseEntity<>(foundUserDTO, token);
    }


    @PostMapping("registration")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> makeRegistration(@RequestBody @Valid RegistrationUserDTO registrationUserDTO,
                                   BindingResult bindingResult) {
        String token;
        try {
             token = authService.signup(registrationUserDTO, bindingResult);
        } catch (MethodArgumentNotValidException e) {
             log.info("Incorrect credentials given by the user");
             return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("jwt-token", token);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .build();
    }

}
