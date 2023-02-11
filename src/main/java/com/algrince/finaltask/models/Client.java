package com.algrince.finaltask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 45, message = "Name should be between 2 and 45 characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 45, message = "Surname should be between 2 and 45 characters")
    private String surname;

    @Column(name = "date_of_birth")
    @NotEmpty(message = "Date of birth should not be empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    // Change after studying Spring Security
    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    // ENUM usage gives NumberFormatException
    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
 //   @NotEmpty
    private ClientRole role;

    @Column(name = "deleted")
//    @NotEmpty
    private boolean isDeleted;

    public Client() {
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
