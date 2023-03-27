package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.repositories.ProductsRepository;
import com.algrince.finaltask.utils.FilterManager;
import com.algrince.finaltask.utils.ProductSpecification;
import com.algrince.finaltask.utils.SearchCriteria;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesService categoriesService;
    private final ProductPropertiesService productPropertiesService;
    private final FilterManager filterManager;
    private final String DELETED_PRODUCT_FILTER = "deletedProductFilter";

    public Page<Product> selectProducts(
            Long categoryId,
            int page, int size,
            String sortField, String sortDir,
            Double minPrice, Double maxPrice,
            List<Long> propertyValues,
            boolean isAdmin) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);

        Pageable paging = PageRequest.of(page, size, direction, sortField);

        List<Specification<Product>> productSpecs = new ArrayList<>();

        Category foundCategory = null;
        if (categoryId != null) {
            foundCategory = categoriesService.findById(categoryId);
        }
        ProductSpecification categorySpec = new ProductSpecification(
                new SearchCriteria("category", ":", foundCategory));
        productSpecs.add(categorySpec);


        if (propertyValues != null) {
            for (Long productPropertyId: propertyValues) {
                ProductProperty foundProductProperty = productPropertiesService.findById(productPropertyId);
                ProductSpecification productPropertySpec = new ProductSpecification(
                        new SearchCriteria(
                                "propertyValues",
                                ":", foundProductProperty.getPropertyValue())
                );
                productSpecs.add(productPropertySpec);
            }
        }

        ProductSpecification minPriceSpec = new ProductSpecification(
                new SearchCriteria("price", ">", minPrice));
        ProductSpecification maxPriceSpec = new ProductSpecification(
                new SearchCriteria("price", "<", maxPrice));
        productSpecs.add(minPriceSpec);
        productSpecs.add(maxPriceSpec);

        Page<Product> products;
        if (isAdmin) {
            products = productsRepository.findAll(
                    Specification.allOf(productSpecs), paging);
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_FILTER);
            products = productsRepository.findAll(
                    Specification.allOf(productSpecs), paging);
            filterManager.disableFilter(DELETED_PRODUCT_FILTER);
        }
        return products;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable paging, boolean isAdmin) {
        Page<Product> products;

        if (isAdmin) {
            products = productsRepository.findAll(paging);
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_FILTER);
            products = productsRepository.findAll(paging);
            filterManager.disableFilter(DELETED_PRODUCT_FILTER);
        }
        return products;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllByCategory(
            Category category, Pageable paging,
            boolean isAdmin) {
        Page<Product> products;

        if (isAdmin) {
            products = productsRepository.findAllByCategory(category, paging);
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_FILTER);
            products = productsRepository.findAllByCategory(category, paging);
            filterManager.disableFilter(DELETED_PRODUCT_FILTER);
        }
        return products;
    }

    @Transactional(readOnly = true)
    public Product findById(Long id, boolean isAdmin) {
        Optional<Product> foundProduct;

        if (isAdmin) {
            foundProduct = productsRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_PRODUCT_FILTER);
            foundProduct = productsRepository.findById(id);
            filterManager.disableFilter(DELETED_PRODUCT_FILTER);
        }
        return foundProduct.orElseThrow(()
                -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Optional<Product> foundProduct = productsRepository.findById(id);
        return foundProduct.orElseThrow(()
                -> new ResourceNotFoundException("Product not found with id: " + id));
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

    @Transactional
    public void updateStock(Long productId, int boughtQuantity) {
        Product foundProduct = findById(productId);
        foundProduct.setStock(
                foundProduct.getStock() - boughtQuantity);
        productsRepository.save(foundProduct);
    }

    @Transactional
    public void restore(Product product) {
        product.setDeleted(false);
        productsRepository.save(product);
    }
}
