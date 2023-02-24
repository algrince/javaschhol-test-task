package com.algrince.finaltask.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class UserListDTO {

    @NotEmpty(message = "{name.notempty}")
    @Size(min = 2, max = 45, message = "{name.size}")
    private String name;


    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 45, message = "Surname should be between 2 and 45 characters")
    private String surname;


    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
}
