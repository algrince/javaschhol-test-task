package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.repositories.CategoriesRepository;
import com.algrince.finaltask.utils.FilterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final FilterManager filterManager;
    private final String DELETED_CATEGORY_FILTER = "deletedCategoryFilter";

    @Transactional(readOnly = true)
    public List<Category> findAll(boolean isAdmin) {
        List<Category> categories;
        if (isAdmin) {
            categories = categoriesRepository.findAll();
        } else {
            filterManager.enableDeletedFilter(DELETED_CATEGORY_FILTER);
            categories = categoriesRepository.findAll();
            filterManager.disableFilter(DELETED_CATEGORY_FILTER);
        }
        return categories;
    }

    @Transactional(readOnly = true)
    public Category findById(Long id, boolean isAdmin) {
        Optional<Category> foundCategory;
        if (isAdmin) {
            foundCategory = categoriesRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_CATEGORY_FILTER);
            foundCategory = categoriesRepository.findById(id);
            filterManager.disableFilter(DELETED_CATEGORY_FILTER);
        }
        return foundCategory.orElseThrow(()
                -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Optional<Category> foundCategory = categoriesRepository.findById(id);
        return foundCategory.orElseThrow(()
                -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional
    public void save(Category category) {
        categoriesRepository.save(category);
    }

    @Transactional
    public void softDelete(Category category) {
        category.setDeleted(true);
        categoriesRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Optional<Category> loadByName(String name) {
        return categoriesRepository.findByName(name);
    }

    @Transactional
    public void restore(Category category) {
        category.setDeleted(false);
        categoriesRepository.save(category);
    }
}
