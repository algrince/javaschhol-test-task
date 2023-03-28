package com.algrince.finaltask.dto.userDTO;

import com.algrince.finaltask.enums.UserRole;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserListDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private UserRole role;

    private boolean isDeleted;
}
