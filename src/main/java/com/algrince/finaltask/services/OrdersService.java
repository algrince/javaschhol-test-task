package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersService usersService;
    private final OrderProductsService orderProductsService;

    public List<Order> selectOrders(Long userId) {
        List<Order> orders = null;
        if (userId != null) {
            User foundUser = usersService.findOne(userId);
            orders = ordersRepository.findByUser(foundUser);
        } else {
            orders = ordersRepository.findAll();
        }
        return orders;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        Optional<Order> foundOrder = ordersRepository.findById(id);
        return foundOrder.orElseThrow(()
                -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return ordersRepository.findAll();
    }

    @Transactional
    public void save(Order order) {
        orderProductsService.updateProductStock(order.getId());
        ordersRepository.save(order);
    }

    @Transactional
    public void softDelete(Order order) {
        order.setDeleted(true);
        ordersRepository.save(order);
    }
}
