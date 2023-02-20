package com.algrince.finaltask.services;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> loadUserByEmail(String email) {
        Optional<User> user = usersRepository.findByEmail(email);
        return user;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Temporally set date on registration
        // Remove when correct form in angular application is set
        Date date = new Date("01/01/2000");
        user.setDateOfBirth(date);
        user.setRole("ROLE_USER");
        user.setDeleted(false);
        usersRepository.save(user);
    }
}
