package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.LoggedUserDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JwtResponseEntity;
import com.algrince.finaltask.services.AuthService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserValidator userValidator;
    private final AuthService authService;
    private final UsersService usersService;
    private final DTOMapper dtoMapper;

    @PostMapping("login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Object> makeLogin(
            @RequestBody AuthenticationDTO authenticationDTO) {
        String token = authService.login(authenticationDTO);

        User foundUser = usersService.findByEmail(authenticationDTO.getEmail());
        LoggedUserDTO foundUserDTO = dtoMapper.mapClass(foundUser, LoggedUserDTO.class);
        return new JwtResponseEntity<>(foundUserDTO, token);
    }


    @PostMapping("registration")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Object> makeRegistration(
            @RequestBody @Valid RegistrationUserDTO registrationUserDTO,
            BindingResult bindingResult) {

        User user = dtoMapper.mapClass(registrationUserDTO, User.class);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("There was a problem during user validation");

            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        log.info("The user has been validated successfully");

        usersService.register(user);

//        String token;
//        try {
//             token = authService.signup(registrationUserDTO, bindingResult);
//        } catch (MethodArgumentNotValidException e) {
//             log.info("Incorrect credentials given by the user");
//             return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
//        }
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("jwt-token", token);
//        return ResponseEntity
//                .ok()
//                .headers(httpHeaders)
//                .build();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
