package com.algrince.finaltask.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class PropertyDTO {

    private Long id;

    @NotEmpty(message = "Property name cannot be empty")
    @Length(max = 45, message = "The property name cannot be longer than 45 characters")
    private String name;

    private boolean isDeleted;
}
