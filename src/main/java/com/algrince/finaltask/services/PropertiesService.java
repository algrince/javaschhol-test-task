package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Property;
import com.algrince.finaltask.repositories.PropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertiesService {

    private final PropertiesRepository propertiesRepository;

    @Transactional(readOnly = true)
    public List<Property> findAll() {
        return propertiesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Property findById(Long id) {
        Optional<Property> foundProperty = propertiesRepository.findById(id);
        return foundProperty.orElse(null);
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
}
