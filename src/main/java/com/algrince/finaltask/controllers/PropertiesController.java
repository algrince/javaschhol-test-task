package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.PropertyDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.services.PropertiesService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import com.algrince.finaltask.validators.PropertyValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("properties")
@RequiredArgsConstructor
public class PropertiesController {

    private final PropertiesService propertiesService;
    private final PropertyValidator propertyValidator;
    private final AccessValidator accessValidator;
    private final DTOMapper dtoMapper;


    @GetMapping
    public List<PropertyDTO> getProperties() {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<Property> properties = propertiesService.findAll(isAdmin);
        return  dtoMapper.mapList(properties, PropertyDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropertyDTO> getProperty(
            @PathVariable("id") Long id) {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        Property foundProperty = propertiesService.findById(id, isAdmin);
        PropertyDTO foundPropertyDTO = dtoMapper.mapClass(foundProperty, PropertyDTO.class);
        return ResponseEntity.ok().body(foundPropertyDTO);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> addProperty(
            @Valid @RequestBody PropertyDTO propertyDTO,
            BindingResult bindingResult) {

        Property property = dtoMapper.mapClass(propertyDTO, Property.class);
        propertyValidator.validate(property, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        propertiesService.save(property);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Object> updateProperty(
            @PathVariable(value = "id") Long propertyId,
            @Valid @RequestBody PropertyDTO propertyDTO,
            BindingResult bindingResult) {

        Property foundProperty = propertiesService.findById(propertyId);
        dtoMapper.mapProperties(propertyDTO, foundProperty);

        propertyValidator.validate(foundProperty, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        propertiesService.save(foundProperty);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<String> deleteProperty(
            @PathVariable(value = "id") Long propertyId) {
        Property propertyToDelete = propertiesService.findById(propertyId);
        propertiesService.softDelete(propertyToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restoreProperty(
            @PathVariable(value = "id") Long propertyId) {
        // Adds possibility to restore soft-deleted property

        Property propertyToRestore = propertiesService.findById(propertyId);
        propertiesService.restore(propertyToRestore);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
