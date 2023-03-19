package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
