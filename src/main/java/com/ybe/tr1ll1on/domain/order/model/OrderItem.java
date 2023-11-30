package com.ybe.tr1ll1on.domain.order.model;

import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer personNumber;

    private Integer price;

    private Boolean reviewWritten;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.REMOVE)
    private Review review;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    public void setReviewWritten(boolean reviewWritten) {
        this.reviewWritten = reviewWritten;
    }

    public boolean getReviewWritten() {
        return reviewWritten;
    }

    public void setId(Long orderItemId) {
        this.id = orderItemId;
    }

    public void setProduct(Product testProduct) {
        this.product = testProduct;
    }
}
