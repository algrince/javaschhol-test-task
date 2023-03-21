package com.algrince.finaltask.dto;

import com.algrince.finaltask.models.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class AddressDTO {

    private Long id;
    @NotEmpty(message = "Country cannot be empty")
    @Length(max = 45, message = "County name cannot be longer than 45 characters")
    private String country;


    @NotEmpty(message = "City cannot be empty")
    @Length(max = 45, message = "City name cannot be longer than 45 characters")
    private String city;

    @NotNull(message = "Postal code cannot be empty")
    @Min(value = 1, message = "Value of postal code is invalid")
    @Max(value = 1000000, message = "Value of postal code is invalid")
    private int postalCode;


    @NotEmpty(message = "Street cannot be empty")
    @Length(max = 45, message = "Street name cannot be longer than 45 characters")
    private String street;


    @NotNull(message = "Home number cannot be empty")
    @Min(value = 1, message = "Home number should be valid")
    private int home;


    @NotNull(message = "Apartment number cannot be empty")
    @Min(value = 1, message = "Apartment number should be valid")
    private int apartment;


    @Length(max = 1, message = "The door indication cannot be longer than 1 character")
    private String door;

    private LoggedUserDTO owner;
}
