package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImagesRepository extends JpaRepository<ProductImage, Long> {

    @Query("select p from ProductImage p where p.product = :product")
    ProductImage findByProductId(@Param("product") Product product);
}
