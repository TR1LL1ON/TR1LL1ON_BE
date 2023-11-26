package com.ybe.tr1ll1on.domain.review.dto.response;

import com.ybe.tr1ll1on.domain.review.model.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewListResponse {

    private Long orderItemId;
    private Long accommodationId;
    private Long productId;
    private Long reviewId;
    private String comment;
    private int rating;
    private LocalDate reviewDate;

    public static ReviewListResponse fromEntity(Review review) {
        return ReviewListResponse.builder()
                .orderItemId(review.getOrderItem().getProduct().getAccommodation().getId())
                .accommodationId(review.getOrderItem().getProduct().getAccommodation().getId())
                .productId(review.getProduct().getId())
                .reviewId(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewDate(review.getReviewDate())
                .build();
    }
}