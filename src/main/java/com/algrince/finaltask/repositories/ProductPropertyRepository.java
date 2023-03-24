package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Long> {

    Optional<ProductProperty> findByPropertyValue(String value);
}
