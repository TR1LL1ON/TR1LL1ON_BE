package com.ybe.tr1ll1on.domain.review.model;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table (name = "review")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private double score;
    private LocalDate reviewDate;
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }
    public void setUser(User user) {
        this.user = user;
    }
    public void setProduct(Product product){
        this.product = product;
    }

    public void update(ReviewUpdateRequest reviewUpdateRequest) {
        this.score = reviewUpdateRequest.getScore();
        this.content = reviewUpdateRequest.getContent();
    }
}
