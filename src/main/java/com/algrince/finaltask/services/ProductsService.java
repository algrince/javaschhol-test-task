package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.repositories.ProductsRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable paging) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProductFilter");
        filter.setParameter("isDeleted", false);
        Page<Product> products = productsRepository.findAll(paging);
        session.disableFilter("deletedProductFilter");
        return products;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllByCategory(Category category, Pageable paging) {
        Page<Product> products = productsRepository.findAllByCategory(category, paging);
        return products;
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Optional<Product> foundProduct = productsRepository.findById(id);
        // Add find by id deleted condition
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
