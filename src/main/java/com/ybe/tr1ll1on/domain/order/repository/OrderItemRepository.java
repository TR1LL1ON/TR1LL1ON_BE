package com.ybe.tr1ll1on.domain.order.repository;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
