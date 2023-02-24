package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.utils.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DTOMapper dtoMapper;
    private final UserValidator userValidator;
    private final UsersService usersService;

    public ResponseEntity<String> login(AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(), authenticationDTO.getPassword());

        // Try login using authentication provider

        try {
            authenticationManager.authenticate(authenticationInputToken);
        } catch (BadCredentialsException e) {
            log.info("Incorrect credentials given by the user");
            return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
        }

        // If login is successful, generate new token
        log.info("The user has correct credentials, proceeding to token generation");

        String token = jwtUtil.generateToken(authenticationDTO.getEmail());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    
    public ResponseEntity<String> signup(
            RegistrationUserDTO registrationUserDTO,
            BindingResult bindingResult) throws MethodArgumentNotValidException {

        User user = dtoMapper.mapClass(registrationUserDTO, User.class);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {


            log.warn("There was a problem during user validation");
            //TODO handle exception
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        log.info("The user has been validated successfully");
        usersService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
