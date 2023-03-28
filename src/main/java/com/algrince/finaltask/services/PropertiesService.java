package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.repositories.PropertiesRepository;
import com.algrince.finaltask.utils.FilterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertiesService {

    private final PropertiesRepository propertiesRepository;
    private final FilterManager filterManager;
    private final String DELETED_PROPERTY_FILTER = "deletedPropertyFilter";

    @Transactional(readOnly = true)
    public List<Property> findAll(boolean isAdmin) {
        List<Property> properties = null;
        if (isAdmin) {
            properties = propertiesRepository.findAll();
        } else {
            filterManager.enableDeletedFilter(DELETED_PROPERTY_FILTER);
            properties = propertiesRepository.findAll();
            filterManager.disableFilter(DELETED_PROPERTY_FILTER);
        }
        return properties;
    }

    @Transactional(readOnly = true)
    public Property findById(Long id, boolean isAdmin) {
        Optional<Property> foundProperty;
        if (isAdmin) {
            foundProperty = propertiesRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_PROPERTY_FILTER);
            foundProperty = propertiesRepository.findById(id);
            filterManager.disableFilter(DELETED_PROPERTY_FILTER);
        }
        return foundProperty.orElseThrow(()
                -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Property findById(Long id) {
        Optional<Property> foundProperty = propertiesRepository.findById(id);
        return foundProperty.orElseThrow(()
                -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    @Transactional
    public void save(Property property) {
        propertiesRepository.save(property);
    }

    @Transactional
    public void softDelete(Property property) {
        property.setDeleted(true);
        propertiesRepository.save(property);
    }
    @Transactional(readOnly = true)
    public Optional<Property> loadByName(String name) {
        return propertiesRepository.findByName(name);
    }

    @Transactional
    public void restore(Property property) {
        property.setDeleted(false);
        propertiesRepository.save(property);
    }

}
