package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.repositories.ProductPropertyRepository;
import com.algrince.finaltask.utils.FilterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPropertiesService {

    private final ProductPropertyRepository productPropertyRepository;
    private final FilterManager filterManager;
    private final String DELETED_PRODUCT_PROPERTY_FILTER = "deletedProductPropertyFilter";


    @Transactional(readOnly = true)
    public List<ProductProperty> findAll(boolean isAdmin) {
        List<ProductProperty> productProperties;
        if (isAdmin) {
            productProperties = productPropertyRepository.findAll();
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_PROPERTY_FILTER);
            productProperties = productPropertyRepository.findAll();
            filterManager.disableFilter(DELETED_PRODUCT_PROPERTY_FILTER);
        }
        return productProperties;
    }

    @Transactional(readOnly = true)
    public ProductProperty findById(Long id, boolean isAdmin) {
        Optional<ProductProperty> foundProductProperty;
        if (isAdmin) {
            foundProductProperty = productPropertyRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_PROPERTY_FILTER);
            foundProductProperty = productPropertyRepository.findById(id);
            filterManager.disableFilter(DELETED_PRODUCT_PROPERTY_FILTER);
        }
        return foundProductProperty.orElseThrow(()
                -> new ResourceNotFoundException("Property value not found with id: " + id));
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
