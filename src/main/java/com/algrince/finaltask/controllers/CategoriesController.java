package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Category category = dtoMapper.mapClass(categoryDTO, Category.class);
        categoriesService.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
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
        CategoryDTO newCategoryDTO = dtoMapper.mapClass(foundCategory, CategoryDTO.class);
        return ResponseEntity.ok().body(newCategoryDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable(value = "id") Long categoryId) {
        Category categoryToDelete = categoriesService.findById(categoryId);
        categoriesService.softDelete(categoryToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
