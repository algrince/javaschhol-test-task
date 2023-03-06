package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
