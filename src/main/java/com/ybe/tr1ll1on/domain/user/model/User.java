package com.ybe.tr1ll1on.domain.user.model;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.security.common.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@Getter
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserFacility userFacility;

    @Builder
    public User(String email, String name, String password, Authority authority) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authority = authority;
    }
}
