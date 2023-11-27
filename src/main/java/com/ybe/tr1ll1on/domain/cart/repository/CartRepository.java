package com.ybe.tr1ll1on.domain.cart.repository;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
}
