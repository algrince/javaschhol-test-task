package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.ProductPropertyDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.services.ProductPropertiesService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import com.algrince.finaltask.validators.ProductPropertyValidator;
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
@RequestMapping("propertyValues")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductPropertiesController {

    private final ProductPropertiesService productPropertiesService;
    private final ProductPropertyValidator productPropertyValidator;
    private final AccessValidator accessValidator;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<ProductPropertyDTO> propertiesValuesIndex() {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<ProductProperty> productProperties = productPropertiesService.findAll(isAdmin);
        return dtoMapper.mapList(productProperties, ProductPropertyDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductPropertyDTO> getProductProperty(
            @PathVariable("id") Long id) {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        ProductProperty foundProductProperty = productPropertiesService.findById(id, isAdmin);
        ProductPropertyDTO foundProductPropertyDTO = dtoMapper.mapClass(foundProductProperty, ProductPropertyDTO.class);
        return ResponseEntity.ok().body(foundProductPropertyDTO);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> addProductProperty(
            @Valid @RequestBody ProductPropertyDTO productPropertyDTO,
            BindingResult bindingResult) {

        ProductProperty productProperty = dtoMapper.mapClass(productPropertyDTO, ProductProperty.class);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        productPropertiesService.save(productProperty);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> updateProductProperty(
            @PathVariable(value = "id") Long productPropertyId,
            @Valid @RequestBody ProductPropertyDTO productPropertyDTO,
            BindingResult bindingResult) {

        ProductProperty foundProductProperty = productPropertiesService.findById(productPropertyId);
        foundProductProperty.setProperty(new Property());
        dtoMapper.mapProperties(productPropertyDTO, foundProductProperty);
        productPropertyValidator.validate(foundProductProperty, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }


        productPropertiesService.save(foundProductProperty);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteProductProperty(
            @PathVariable(value = "id") Long productPropertyId) {
        ProductProperty productPropertyToDelete =
                productPropertiesService.findById(productPropertyId);
        productPropertiesService.softDelete(productPropertyToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreProductProperty(
            @PathVariable(value = "id") Long productPropertyId) {
        ProductProperty productPropertyToRestore =
                productPropertiesService.findById(productPropertyId);
        productPropertiesService.restore(productPropertyToRestore);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
