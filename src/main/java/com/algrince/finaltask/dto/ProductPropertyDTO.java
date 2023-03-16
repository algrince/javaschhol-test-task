package com.algrince.finaltask.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductPropertyDTO {

    private Long id;
    private PropertyDTO property;
    private String propertyValue;
}
