package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

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
}
