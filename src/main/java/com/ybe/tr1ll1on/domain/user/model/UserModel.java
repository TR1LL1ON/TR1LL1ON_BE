package com.ybe.tr1ll1on.domain.user.model;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Comment("회원")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String email;
    private String password;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Cart> carts;
}
