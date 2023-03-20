package com.algrince.finaltask.controllers;

import com.algrince.finaltask.dto.PropertyDTO;
import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.services.PropertiesService;
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
@RequestMapping("properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PropertiesController {

    private final PropertiesService propertiesService;
    private final DTOMapper dtoMapper;

    @GetMapping
    public List<PropertyDTO> propertiesIndex() {
        List<Property> properties = propertiesService.findAll();
        return  dtoMapper.mapList(properties, PropertyDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropertyDTO> getProperty(
            @PathVariable("id") Long id) {
        Property foundProperty = propertiesService.findById(id);
        PropertyDTO foundPropertyDTO = dtoMapper.mapClass(foundProperty, PropertyDTO.class);
        return ResponseEntity.ok().body(foundPropertyDTO);
    }

    @PostMapping
    public ResponseEntity<Object> addProperty(
            @Valid @RequestBody PropertyDTO propertyDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Property property = dtoMapper.mapClass(propertyDTO, Property.class);
        propertiesService.save(property);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateProperty (
            @PathVariable(value = "id") Long propertyId,
            @Valid @RequestBody PropertyDTO propertyDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Property foundProperty = propertiesService.findById(propertyId);
        dtoMapper.mapProperties(propertyDTO, foundProperty);
        propertiesService.save(foundProperty);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProperty (@PathVariable(value = "id") Long propertyId) {
        Property propertyToDelete = propertiesService.findById(propertyId);
        propertiesService.softDelete(propertyToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
