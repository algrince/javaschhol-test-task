package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
