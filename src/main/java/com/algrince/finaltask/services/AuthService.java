package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.utils.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DTOMapper dtoMapper;
    private final UserValidator userValidator;
    private final UsersService usersService;

    public String login(AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(), authenticationDTO.getPassword());

        // Try login using authentication provider

        authenticationManager.authenticate(authenticationInputToken);

        // If login is successful, generate new token
        log.info("The user has correct credentials, proceeding to token generation");

        return jwtUtil.generateToken(authenticationDTO.getEmail());
    }
    
//    public String signup(
//            RegistrationUserDTO registrationUserDTO,
//            BindingResult bindingResult) throws MethodArgumentNotValidException {
//
//        User user = dtoMapper.mapClass(registrationUserDTO, User.class);
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//
//            log.warn("There was a problem during user validation");

//            throw new MethodArgumentNotValidException(null, bindingResult);
//        }
//        log.info("The user has been validated successfully");
//        usersService.register(user);
//
//        return jwtUtil.generateToken(user.getEmail());
//    }
}
