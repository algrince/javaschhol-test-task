package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import com.algrince.finaltask.validators.CategoryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final CategoryValidator categoryValidator;
    private final AccessValidator accessValidator;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<CategoryDTO> getCategoriesList() {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<Category> categories = categoriesService.findAll(isAdmin);
        return dtoMapper.mapList(categories, CategoryDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(
            @PathVariable("id") Long id) {
        Category foundCategory = categoriesService.findById(id);
        CategoryDTO foundCategoryDTO = dtoMapper.mapClass(foundCategory, CategoryDTO.class);
        return ResponseEntity.ok().body(foundCategoryDTO);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> addCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {

        Category category = dtoMapper.mapClass(categoryDTO, Category.class);
        categoryValidator.validate(category, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        categoriesService.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> updateCategory(
            @PathVariable(value = "id") Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {

        Category foundCategory = categoriesService.findById(categoryId);
        dtoMapper.mapProperties(categoryDTO, foundCategory);
        categoryValidator.validate(foundCategory, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        categoriesService.save(foundCategory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteCategory(
            @PathVariable(value = "id") Long categoryId) {
        Category categoryToDelete = categoriesService.findById(categoryId);
        categoriesService.softDelete(categoryToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreCategory(
            @PathVariable(value = "id") Long categoryId) {
        // Adds possibility to restore soft-deleted categories

        Category categoryToRestore = categoriesService.findById(categoryId);
        categoriesService.restore(categoryToRestore);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
