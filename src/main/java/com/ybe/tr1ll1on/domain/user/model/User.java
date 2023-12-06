package com.ybe.tr1ll1on.domain.user.model;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.security.common.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Orders> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewList = new ArrayList<>();

    public void setId(Long userId) {
        this.id = userId;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
