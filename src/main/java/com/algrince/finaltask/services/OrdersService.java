package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersService usersService;
    private final OrderProductsService orderProductsService;

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
        ordersRepository.save(order);
    }

    @Transactional
    public void saveAndApplyChanges(Order order) {
        Order savedOrder = ordersRepository.save(order);
        orderProductsService.updateProductStock(savedOrder.getId());
    }

    @Transactional
    public void softDelete(Order order) {
        order.setDeleted(true);
        ordersRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findByUser(User user) {
        return ordersRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllPaidInPeriod(Calendar start, Calendar finish) {
        return ordersRepository.findAllPaidBetweenStartAndFinishCreationDate(start, finish);
    }

    @Transactional(readOnly = true)
    public List<Object[]> findTop10ByUsers() {
        return ordersRepository.findTop10ByUsers();
    }
}
