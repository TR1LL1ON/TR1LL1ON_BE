package com.ybe.tr1ll1on.domain.review.model;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table (name = "review")
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private double score;
    private LocalDate reviewDate;
    private String content;

    @OneToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Review (Long id, String content, double score, LocalDate reviewDate) {
        this.id = id;
        this.score = score;
        this.reviewDate = reviewDate;
        this.content = content;
    }

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
