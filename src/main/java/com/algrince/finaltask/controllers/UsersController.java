package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.userDTO.DetailedUserDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.dto.userDTO.LoggedUserDTO;
import com.algrince.finaltask.dto.userDTO.UpdatePassDTO;
import com.algrince.finaltask.dto.userDTO.UserListDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import com.algrince.finaltask.validators.PasswordValidator;
import com.algrince.finaltask.validators.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {
    private final PasswordValidator passwordValidator;
    private final AccessValidator accessValidator;
    private final UserValidator userValidator;
    private final UsersService usersService;
    private final DTOMapper dtoMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<UserListDTO> getUsers() {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<User> users = usersService.findAll(isAdmin);
        return dtoMapper.mapList(users, UserListDTO.class);
    }

    @PostMapping
    void addUser(@RequestBody User user) {
        usersService.register(user);
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getUser(
            @PathVariable("id") Long id,
            Principal principal) {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        User foundUser = usersService.findById(id, isAdmin);
        usersService.checkAccess(principal, foundUser);
        DetailedUserDTO foundUserDTO = dtoMapper.mapClass(foundUser, DetailedUserDTO.class);
        return ResponseEntity.ok().body(foundUserDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateUser(
            @PathVariable(value = "id") Long userId,
            @Valid @RequestBody RegistrationUserDTO registrationUserDTO,
            Principal principal, BindingResult bindingResult) {
        User foundUser = usersService.findById(userId);
        usersService.checkAccess(principal, foundUser);

        User userToValidate = dtoMapper.mapClass(registrationUserDTO, User.class);
        userToValidate.setId(userId);
        userValidator.validate(userToValidate, bindingResult);
        passwordValidator.validate(userToValidate, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        dtoMapper.mapProperties(registrationUserDTO, foundUser);
        usersService.update(foundUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateUserPassword(
            @PathVariable(value = "id") Long userId,
            @Valid @RequestBody UpdatePassDTO updatePassDTO,
            Principal principal, BindingResult bindingResult) {
        User foundUser = usersService.findById(userId);
        usersService.checkAccess(principal, foundUser);

        dtoMapper.mapProperties(updatePassDTO, foundUser);
        passwordValidator.validate(foundUser, bindingResult);

        foundUser.setPassword(updatePassDTO.getNewPassword());
        userValidator.validate(foundUser, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        usersService.update(foundUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateUserRole(
            @PathVariable(value = "id") Long userId,
            LoggedUserDTO loggedUserDTO) {
        User userToPromote = usersService.findById(userId);
        userToPromote.setRole(loggedUserDTO.getRole());
        usersService.save(userToPromote);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser(
            @PathVariable(value ="id") Long userId,
            Principal principal) {
        User userToDelete = usersService.findById(userId);
        usersService.checkAccess(principal, userToDelete);
        usersService.softDelete(userToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreUser(
            @PathVariable(value = "id") Long userId) {
        User userToRestore = usersService.findById(userId);
        usersService.restore(userToRestore);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
