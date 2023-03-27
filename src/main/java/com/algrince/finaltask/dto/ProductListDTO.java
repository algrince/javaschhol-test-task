package com.algrince.finaltask.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductListDTO {

    private Long id;

    private String title;

    private Double price;

    private int stock;

    private Double volume;

    private Double weight;

    private CategoryDTO category;

    private List<ProductPropertyDTO> propertyValues;

    private boolean isDeleted;

    private String image;
}
