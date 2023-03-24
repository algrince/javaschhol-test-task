package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
