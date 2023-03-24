package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.CategoryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final CategoryValidator categoryValidator;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<CategoryDTO> categoriesIndex() {
        List<Category> categories = categoriesService.findAll();
        return dtoMapper.mapList(categories, CategoryDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory (@PathVariable("id") Long id) {
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


        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }


        categoriesService.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> updateCategory (
            @PathVariable(value = "id") Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Category foundCategory = categoriesService.findById(categoryId);
        dtoMapper.mapProperties(categoryDTO, foundCategory);
        categoriesService.save(foundCategory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteCategory (@PathVariable(value = "id") Long categoryId) {
        Category categoryToDelete = categoriesService.findById(categoryId);
        categoriesService.softDelete(categoryToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
