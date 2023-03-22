package com.algrince.finaltask.dto;

import com.algrince.finaltask.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggedUserDTO {

    private Long id;

    private UserRole role;

    private String email;
}
