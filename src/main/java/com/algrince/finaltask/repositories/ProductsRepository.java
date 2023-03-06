package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {

}
