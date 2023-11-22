package com.ybe.tr1ll1on.domain.cart.model;

import com.ybe.tr1ll1on.domain.user.model.UserModel;
import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    private UserModel userModel;
}
