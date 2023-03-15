package com.algrince.finaltask.services;

import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.repositories.ProductPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPropertiesService {

    private final ProductPropertyRepository productPropertyRepository;

    @Transactional(readOnly = true)
    public List<ProductProperty> findAll() {
        return productPropertyRepository.findAll();
    }
}
