package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.repositories.ProductsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProductFilter");
        filter.setParameter("isDeleted", false);
        List<Product> products = productsRepository.findAll();
        session.disableFilter("deletedProductFilter");
        return products;
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
