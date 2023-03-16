package com.algrince.finaltask.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductsDTO {

    private Long id;

    private String title;

    private Double price;

    private int stock;

    private Double volume;

    private Double weight;

    private CategoryDTO category;
}
