package com.ybe.tr1ll1on.domain.order.repository;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select o from Orders o left join fetch o.orderItemList where o.id = :orderId")
    Optional<Orders> findById(@Param("orderId") Long orderId);
}
