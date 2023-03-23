package com.algrince.finaltask.utils;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
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

//        Check if email is repeated
        if (clientFromDB.isPresent()) {
           errors.rejectValue("email", "user.email.exists", "The user with this email already exists");
        }

        LocalDate dateOfBirth = ((User) target).getDateOfBirth();

        if (dateOfBirth != null) {
//            Check if the age of the user is between 100 and 14 yo
            LocalDate olderDate = LocalDate.parse("1923-01-01");
            LocalDate newerDate = LocalDate.parse("2009-01-01");

            if (dateOfBirth.isBefore(olderDate) || dateOfBirth.isAfter(newerDate)) {
                errors.rejectValue("dateOfBirth", "user.dateOfBirth.invalid",
                        "Please introduce valid date of birth (by our policy, you can shop in our store if you are younger than 100 and older than 14 y.o.");
            }
        }

//        Check if password at least one lowercase letter, one uppercase letter, one digit, and one special character
        String password = ((User) target).getPassword();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]*$";
        if (password != null & !password.matches(regex)) {
            errors.rejectValue("password", "user.password.invalid",
                    "Password should contain at least one lowercase letter, one uppercase letter, one digit, and one special character");
        }
    }
}
