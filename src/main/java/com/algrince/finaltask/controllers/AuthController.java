package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.services.AuthService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.utils.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity makeLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        return authService.login(authenticationDTO);
    }


    @PostMapping("registration")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> makeRegistration(@RequestBody @Valid RegistrationUserDTO registrationUserDTO,
                                   BindingResult bindingResult) throws MethodArgumentNotValidException {
        return authService.signup(registrationUserDTO, bindingResult);
    }

}
