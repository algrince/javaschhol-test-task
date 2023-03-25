package com.algrince.finaltask.dto;

import com.algrince.finaltask.models.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderProductDTO {

    private int quantity;
    private Long addedProductId;

    private ProductsDTO product;
}
