package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements Validator {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> userFromDB = usersService.loadByEmail(user.getEmail());

        String userFromDBPassword = userFromDB.map(User::getPassword).orElse(null);
        String targetUserRawPassword = user.getPassword();

        if (!passwordEncoder.matches(targetUserRawPassword, userFromDBPassword)) {
            errors.rejectValue(
                    "password",
                    "user.password.user.password.notmatch",
                    "Introduced password does not match the one assigned to this user"
            );
        }
    }
}
