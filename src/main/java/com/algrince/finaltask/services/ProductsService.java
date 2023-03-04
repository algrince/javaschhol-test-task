package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Optional<Product> foundProduct = productsRepository.findById(id);
        return foundProduct.orElse(null);
    }

    @Transactional
    public void save(Product product) {
        productsRepository.save(product);
    }

    @Transactional
    public void softDelete(Product product) {
        product.setDeleted(true);
        productsRepository.save(product);
    }
}
