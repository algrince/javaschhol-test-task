package com.algrince.finaltask.services;

import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.OrderProduct;
import com.algrince.finaltask.repositories.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductsService {

    private final OrderProductRepository orderProductRepository;
    private final ProductsService productsService;

    @Transactional(readOnly = true)
    public List<OrderProduct> findAllByOrderId(Long id) {
        return orderProductRepository.findAllByOrderId(id);
    }

    @Transactional
    public void updateProductStock(Long orderId) {
        List<OrderProduct> foundOrderProducts = findAllByOrderId(orderId);
        for (OrderProduct element: foundOrderProducts) {
            productsService.updateStock(
                    element.getProduct().getId(),
                    element.getQuantity());
        }
    }

    @Transactional(readOnly = true)
    public List<Object[]> findTop10ByProduct() {
        return orderProductRepository.findTop10ByProduct();
    }

}
