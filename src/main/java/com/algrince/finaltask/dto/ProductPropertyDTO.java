package com.algrince.finaltask.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class ProductPropertyDTO {

    private Long id;

    @NotNull(message = "Property needs to be chosen")
    private PropertyDTO property;

    @NotEmpty(message = "Property value cannot be empty")
    @Length(max = 45, message = "The property value cannot be longer than 45 characters")
    private String propertyValue;
}
