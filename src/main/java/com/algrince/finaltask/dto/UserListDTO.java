package com.algrince.finaltask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserListDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;
}
