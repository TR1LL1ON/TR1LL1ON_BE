package com.ybe.tr1ll1on.domain.cart.repository;

import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
