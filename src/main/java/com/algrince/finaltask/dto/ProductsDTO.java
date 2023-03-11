package com.algrince.finaltask.dto;

import com.algrince.finaltask.models.Category;
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

    private Category category;
}
