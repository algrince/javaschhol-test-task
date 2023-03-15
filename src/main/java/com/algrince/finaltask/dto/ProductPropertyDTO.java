package com.algrince.finaltask.dto;

import com.algrince.finaltask.models.Property;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPropertyDTO {

    private Long id;
    private Property property;
    private String propertyValue;
}
