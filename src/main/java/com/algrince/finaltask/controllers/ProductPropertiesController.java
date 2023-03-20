package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.ProductPropertyDTO;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.services.ProductPropertiesService;
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

    @GetMapping("{id}")
    public ResponseEntity<ProductPropertyDTO> getProductProperty(
            @PathVariable("id") Long id) {
        ProductProperty foundProductProperty = productPropertiesService.findById(id);
        ProductPropertyDTO foundProductPropertyDTO = dtoMapper.mapClass(foundProductProperty, ProductPropertyDTO.class);
        return ResponseEntity.ok().body(foundProductPropertyDTO);
    }

    @PostMapping
    public ResponseEntity<Object> addProductProperty(
            @Valid @RequestBody ProductPropertyDTO productPropertyDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        ProductProperty productProperty = dtoMapper.mapClass(productPropertyDTO, ProductProperty.class);
        productPropertiesService.save(productProperty);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateProductProperty(
            @PathVariable(value = "id") Long productPropertyId,
            @Valid @RequestBody ProductPropertyDTO productPropertyDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        ProductProperty foundProductProperty = productPropertiesService.findById(productPropertyId);
        dtoMapper.mapProperties(productPropertyDTO, foundProductProperty);
        productPropertiesService.save(foundProductProperty);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductProperty(
            @PathVariable(value = "id") Long productPropertyId) {
        ProductProperty productPropertyToDelete = productPropertiesService.findById(productPropertyId);
        productPropertiesService.softDelete(productPropertyToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
