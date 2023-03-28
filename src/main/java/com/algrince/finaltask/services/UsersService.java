package com.algrince.finaltask.services;

import com.algrince.finaltask.enums.UserRole;
import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.UsersRepository;
import com.algrince.finaltask.utils.FilterManager;
import com.algrince.finaltask.validators.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final AccessValidator accessValidator;
    private final PasswordEncoder passwordEncoder;
    private final FilterManager filterManager;
    private final String DELETED_USER_FILTER = "deletedUserFilter";

    @Transactional(readOnly = true)
    public List<User> findAll(boolean isAdmin) {
        List<User> users = null;
        if (isAdmin) {
            users = usersRepository.findAll();
        } else {
            filterManager.enableDeletedFilter(DELETED_USER_FILTER);
            users = usersRepository.findAll();
            filterManager.disableFilter(DELETED_USER_FILTER);
        }
        return users;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        filterManager.enableDeletedFilter(DELETED_USER_FILTER);
        Optional<User> foundUser = usersRepository.findByEmail(email);
        filterManager.disableFilter(DELETED_USER_FILTER);
        return foundUser.orElseThrow(()
            -> new ResourceNotFoundException("User not found with email " + email));
    }

    @Transactional(readOnly = true)
    public Optional<User> loadByEmail(String email) {
        Optional<User> foundUser = usersRepository.findByEmail(email);
        return foundUser;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElseThrow(()
                -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User findById(Long id, boolean isAdmin) {
        Optional<User> foundUser;
        if (isAdmin) {
            foundUser = usersRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_USER_FILTER);
            foundUser = usersRepository.findById(id);
            filterManager.disableFilter(DELETED_USER_FILTER);
        }
        return foundUser.orElseThrow(()
                -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.BUYER);
        user.setDeleted(false);
        usersRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    public void softDelete(User user) {
        user.setDeleted(true);
        usersRepository.save(user);
    }

    public void checkAccess(Principal principal, User user) {
        if (!accessValidator.isAccessible(principal, user)) {
            throw new AccessDeniedException("User has no rights to access this information");
        }
    }

    public void restore(User user) {
        user.setDeleted(false);
        usersRepository.save(user);
    }
}
