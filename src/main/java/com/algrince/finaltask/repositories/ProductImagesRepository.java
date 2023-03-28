package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImage, Long> {
}
