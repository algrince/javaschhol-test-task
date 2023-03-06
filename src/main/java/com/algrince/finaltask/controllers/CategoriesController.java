package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
