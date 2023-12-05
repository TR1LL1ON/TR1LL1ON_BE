package com.ybe.tr1ll1on.domain.order.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.global.common.Payment;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderCreateDate;
    private Payment payment;
    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
