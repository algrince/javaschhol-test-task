package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.category = :category")
    Page<Product> findAllByCategory(@Param("category") Long categoryId,
                                    Pageable pageable);
}
