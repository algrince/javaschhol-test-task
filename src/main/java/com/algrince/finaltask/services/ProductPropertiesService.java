package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.repositories.ProductPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPropertiesService {

    private final ProductPropertyRepository productPropertyRepository;

    @Transactional(readOnly = true)
    public List<ProductProperty> findAll() {
        return productPropertyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductProperty findById(Long id) {
        Optional<ProductProperty> foundProductProperty = productPropertyRepository.findById(id);
        return foundProductProperty.orElseThrow(()
                -> new ResourceNotFoundException("Property value not found with id: " + id));
    }

    @Transactional
    public void save(ProductProperty productProperty) {
        productPropertyRepository.save(productProperty);
    }

    @Transactional
    public void softDelete(ProductProperty productProperty) {
        productProperty.setDeleted(true);
        productPropertyRepository.save(productProperty);
    }

    @Transactional(readOnly = true)
    public Optional<ProductProperty> loadByValue(String value) {
        return productPropertyRepository.findByPropertyValue(value);
    }
}
