package com.ybe.tr1ll1on.domain.order.repository;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select distinct o " +
            "from Orders o " +
            "join fetch o.orderItemList oi " +
            "join fetch oi.product p " +
            "join fetch p.accommodation a " +
            "where o.user = :user " +
            "order by o.orderCreateDate desc")
    List<Orders> getOrderListByUser(@Param("user") User user);

    @Query("select distinct o " +
            "from Orders o " +
            "join fetch o.orderItemList oi " +
            "join fetch oi.product p " +
            "join fetch p.accommodation a " +
            "where o.id = :orderId")
    Optional<Orders> getOrdersByIdWithDetails(@Param("orderId") Long orderId);
}