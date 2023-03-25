package com.algrince.finaltask.validators;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class AccessValidator {

    public boolean isAccessible(
            Principal principal,
            User foundUser) {

        return (authUserIsEmployee() ||
                authUserIsAdmin() ||
                Objects.equals(principal.getName(), foundUser.getEmail()));
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        return roles.get(0);
    }

    public boolean authUserIsEmployee() {
        return getRole().equals("ROLE_EMPLOYEE");
    }

    public boolean authUserIsAdmin() {
        return getRole().equals("ROLE_ADMIN");
    }
}
