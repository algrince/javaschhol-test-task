package com.algrince.finaltask.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {

    private Long id;
    @NotEmpty
    private String country;


    @NotEmpty
    private String city;

    @NotNull
    private int postalCode;


    @NotEmpty
    private String street;


    @NotNull
    private int home;


    @NotNull
    private int apartment;


    @Size(max = 1)
    private String door;
}
