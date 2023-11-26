package com.ybe.tr1ll1on.domain.review.dto.response;

import com.ybe.tr1ll1on.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateResponse {

    private String message;
    private ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDetails {
        private Long userId;
        private Long orderItemId;
        private Long accommodationId;
        private Long productId;
        private Long reviewId;
        private String comment;
        private double rating;
        private LocalDate reviewDate;
    }

    public static ReviewCreateResponse fromEntity(Review review) {
        return ReviewCreateResponse.builder()
                .message("리뷰가 성공적으로 작성되었습니다.")
                .review(ReviewDetails.builder()
                        .userId(review.getUser().getId())
                        .orderItemId(review.getOrderItem().getId())
                        .accommodationId(review.getOrderItem().getProduct().getAccommodation().getId())
                        .productId(review.getProduct().getId())
                        .reviewId(review.getId())
                        .comment(review.getComment())
                        .rating(review.getRating())
                        .reviewDate(review.getReviewDate())
                        .build())
                .build();
    }
}