package com.algrince.finaltask.controllers;


import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

    private final UsersService usersService;

    @GetMapping()
    public List<User> userIndex() {
        return usersService.findAll();
    }


    @PostMapping()
    void addUser(@RequestBody User user) {
        usersService.register(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return usersService.findOne(id);
    }
}
