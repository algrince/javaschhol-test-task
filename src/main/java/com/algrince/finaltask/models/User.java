package com.algrince.finaltask.models;

import com.algrince.finaltask.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(
        name = "isDeleted",
        type = org.hibernate.type.descriptor.java.BooleanJavaType.class))
@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 45, message = "Name should not be longer than 45 characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 45, message = "Surname should be between 2 and 45 characters")
    private String surname;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth should not be empty")
    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "deleted")
    private boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade={CascadeType.ALL})
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
