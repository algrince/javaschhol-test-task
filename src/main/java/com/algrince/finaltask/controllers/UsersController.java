package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.DetailedUserDTO;
import com.algrince.finaltask.dto.RegistrationUserDTO;
import com.algrince.finaltask.dto.UserListDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UsersService usersService;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<UserListDTO> userIndex() {
        List<User> users = usersService.findAll();
        return dtoMapper.mapList(users, UserListDTO.class);
    }

    @PostMapping
    void addUser(@RequestBody User user) {
        usersService.register(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailedUserDTO> getUser(@PathVariable("id") Long id) {
        User foundUser = usersService.findOne(id);
        DetailedUserDTO foundUserDTO = dtoMapper.mapClass(foundUser, DetailedUserDTO.class);
        return ResponseEntity.ok().body(foundUserDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable(value = "id") Long userId,
            @Valid @RequestBody RegistrationUserDTO registrationUserDTO) {
        User foundUser = usersService.findOne(userId);
        dtoMapper.mapProperties(registrationUserDTO, foundUser);
        usersService.save(foundUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser (@PathVariable(value ="id") Long userId) {
        User userToDelete = usersService.findOne(userId);
        usersService.softDelete(userToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
