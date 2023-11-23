package com.ybe.tr1ll1on.domain.order.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.global.common.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date orderCreateDate;
    private Payment payment;
    private Integer totalPrice;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
