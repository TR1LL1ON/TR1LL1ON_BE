package com.ybe.tr1ll1on.domain.order.model;

import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.model.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
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

    private boolean reviewWritten;

    @OneToOne
    @JoinColumn(name = "review_id")
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
}
