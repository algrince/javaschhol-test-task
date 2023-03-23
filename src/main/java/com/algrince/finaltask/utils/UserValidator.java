package com.algrince.finaltask.utils;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> clientFromDB = usersService.loadByEmail(user.getEmail());

        if (clientFromDB.isPresent()) {
           errors.rejectValue("email", "user.email.exists", "The user with this email already exists");
        }
    }
}
