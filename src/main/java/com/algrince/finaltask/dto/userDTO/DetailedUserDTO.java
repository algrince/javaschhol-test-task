package com.algrince.finaltask.dto.userDTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DetailedUserDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 45, message = "Name should not be longer than 45 characters")
    private String name;


    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 45, message = "Surname should be between 2 and 45 characters")
    private String surname;


    @NotNull(message = "Date of birth should not be empty")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;


    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email is invalid")
    private String email;

}
