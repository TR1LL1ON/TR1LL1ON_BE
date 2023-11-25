package com.ybe.tr1ll1on.review.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.review.dto.request.ReviewUpdateRequest;
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

    private String comment;
    private int rating;
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Review (Long id, String comment, int rating, LocalDate reviewDate) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public void update(ReviewUpdateRequest reviewUpdateRequest) {
        this.comment = comment;
        this.rating = rating;
    }
}
