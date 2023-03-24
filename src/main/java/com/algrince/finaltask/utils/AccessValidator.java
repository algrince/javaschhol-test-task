package com.algrince.finaltask.utils;

import com.algrince.finaltask.models.User;
import com.algrince.finaltask.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

@Component
public class AccessValidator {

    public boolean isAccessible(
            Principal principal,
            User foundUser) {

        String role = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                role = authority.getAuthority();
            }
        }

        return (Objects.equals(role, "ROLE_EMPLOYEE") ||
                Objects.equals(role, "ROLE_ADMIN") ||
                Objects.equals(principal.getName(), foundUser.getEmail()));
    }
}
