package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.ProductPropertyDTO;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.services.ProductPropertiesService;
import com.algrince.finaltask.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("propertyValues")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductPropertiesController {

    private final ProductPropertiesService productPropertiesService;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<ProductPropertyDTO> propertiesValuesIndex() {
        List<ProductProperty> productProperties = productPropertiesService.findAll();
        return dtoMapper.mapList(productProperties, ProductPropertyDTO.class);
    }

}
