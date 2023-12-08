package com.ybe.tr1ll1on.domain.order.model;

import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.global.common.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer personNumber;
    private Integer price;

    private ReviewStatus reviewStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    public void setId(Long orderItemId) {
        this.id = orderItemId;
    }

    public void setProduct(Product testProduct) {
        this.product = testProduct;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
