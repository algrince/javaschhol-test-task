package com.algrince.finaltask.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    @Column(name = "name")
    @NotEmpty(message = "{email.notempty}")
    @Size(min = 2, max = 45, message = "{name.size}")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 45, message = "Surname should be between 2 and 45 characters")
    private String surname;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth should not be empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
