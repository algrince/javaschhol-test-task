package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrderId(Long id);
    @Query("select o.product, count(o.id) as occurrences from OrderProduct o group by o.product order by occurrences desc")
    List<Object[]> findTop10ByProduct();
}
