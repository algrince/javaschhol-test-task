package com.algrince.finaltask.services;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.UsersRepository;
import com.algrince.finaltask.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Account not found");

        return new UserDetails(user.get());
    }
}
