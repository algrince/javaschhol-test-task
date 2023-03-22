package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.models.Product;
import com.algrince.finaltask.models.ProductProperty;
import com.algrince.finaltask.repositories.ProductsRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesService categoriesService;
    private final ProductPropertiesService productPropertiesService;
    private final EntityManager entityManager;

    public Page<Product> selectProducts(
            Long categoryId,
            int page, int size,
            String sortField, String sortDir,
            Double minPrice, Double maxPrice,
            List<Long> prValues) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);

        Pageable paging = PageRequest.of(page, size, direction, sortField);

        List<ProductSpecification> productSpecs = new ArrayList<>();

        Category foundCategory = null;
        if (categoryId != null) {
            foundCategory = categoriesService.findById(categoryId);
        }
        ProductSpecification categorySpec = new ProductSpecification(
                new SearchCriteria("category", ":", foundCategory));
        productSpecs.add(categorySpec);


        List<ProductProperty> foundProductProperties = new ArrayList<>();

        if (!prValues.isEmpty()) {
            for (Long ppId: prValues) {
                ProductProperty foundProductProperty = productPropertiesService.findById(ppId);
                ProductSpecification productPropertySpec = new ProductSpecification(
                        new SearchCriteria("propertyValues", ":", foundProductProperty)
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

        Iterable<ProductSpecification> iterableSpecs = productSpecs;
//        Page<Product> products = productsRepository.findAll(
//                Specification.allOf(iterableSpecs), paging);
        Page<Product> products = productsRepository.findAll(
                Specification.allOf(categorySpec, minPriceSpec, maxPriceSpec), paging);
        return products;
    }

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
}
