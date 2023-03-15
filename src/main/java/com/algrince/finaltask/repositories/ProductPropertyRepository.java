package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Long> {
}
