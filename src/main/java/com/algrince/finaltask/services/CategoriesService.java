package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Optional<Category> foundCategory = categoriesRepository.findById(id);
        return foundCategory.orElse(null);
    }
}
