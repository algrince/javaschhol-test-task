package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.AuthenticationDTO;
import com.algrince.finaltask.dto.UserDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.JWTUtil;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final UsersService usersService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    // private final ProviderManager providerManager;

    // @GetMapping("/login")
    // public String loginPage() {
    //     return "auth/login";
    // }

    @PostMapping("/login")
    public Map<String, String> makeLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(), authenticationDTO.getPassword());

        // Try login using authentication provider
        // try {
        //    providerManager.authenticate(authenticationInputToken);
        // } catch (BadCredentialsException e) {
        //     return Map.of("message", "Incorrect credentials");
        // }

        // If login is successful, generate new token
        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
        return Map.of("jwt-token", token);
    }

    // @GetMapping("/registration")
    // public String registrationPage(@ModelAttribute("user") User user) {
    //     return "auth/registration";
    // }

    @PostMapping("/registration")
    public Map<String, String> makeRegistration(@RequestBody @Valid UserDTO userDTO,
                                   BindingResult bindingResult) {
        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "error");
        }

       usersService.register(user);

       String token = jwtUtil.generateToken(user.getEmail());
       return Map.of("jwt-token", token);
    }

    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
