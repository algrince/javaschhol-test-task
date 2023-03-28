package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.userDTO.AuthenticationDTO;
import com.algrince.finaltask.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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
}
