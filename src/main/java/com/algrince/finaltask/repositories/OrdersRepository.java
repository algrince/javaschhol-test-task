package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Order;
import com.algrince.finaltask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("select o from Order o where o.paymentStatus = 'PAID' and o.createdDate between :start and :finish")
    List<Order> findAllPaidBetweenStartAndFinishCreationDate(@Param("start") Calendar start,
                                                         @Param("finish") Calendar finish);

    @Query("select o.user, count(o.id) as occurrences from Order o group by o.user order by occurrences desc limit 10")
    List<Object[]> findTop10ByUsers();
}
