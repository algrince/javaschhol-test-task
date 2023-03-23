package com.algrince.finaltask.services;

import com.algrince.finaltask.enums.UserRole;
import com.algrince.finaltask.exceptions.ResourceNotFoundException;
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
    public User findByEmail(String email) {
        Optional<User> foundUser = usersRepository.findByEmail(email);
        return foundUser.orElseThrow(()
            -> new ResourceNotFoundException("User not found with email " + email));
    }

    @Transactional(readOnly = true)
    public Optional<User> loadByEmail(String email) {
        Optional<User> foundUser = usersRepository.findByEmail(email);
        return foundUser;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//         Temporally set date on registration
//         Remove when correct form in angular application is set
//        Date date = new Date("01/01/2000");
//        user.setDateOfBirth(date);
        user.setRole(UserRole.BUYER);
        user.setDeleted(false);
        usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElseThrow(()
                -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void softDelete(User user) {
        user.setDeleted(true);
        usersRepository.save(user);
    }
}
