package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.DetailedUserDTO;
import com.algrince.finaltask.dto.UserListDTO;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
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
        // TODO introduce user not found handling
        return ResponseEntity.ok().body(foundUserDTO);
    }
}
