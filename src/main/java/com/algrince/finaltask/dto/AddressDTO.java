package com.algrince.finaltask.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {

    
    @NotEmpty
    private String country;


    @NotEmpty
    private String city;


    @NotEmpty
    @Size(min = 5, max = 6)
    private int postalCode;


    @NotEmpty
    private String street;


    @NotEmpty
    private int home;


    @NotEmpty
    private int apartment;


    @Size(max = 1)
    private String door;
}
