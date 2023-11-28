package com.ybe.tr1ll1on.domain.review.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

        private Long reviewId;
        private LocalDate reviewDate;
        private double score;
        private Long userId;
        private Long orderItemId;
        private Long accommodationId;
        private Long productId;
        private String content;

    }

    public static ReviewCreateResponse fromEntity(Review review) {
        return ReviewCreateResponse.builder()
                .message("리뷰가 성공적으로 작성되었습니다.")
                .review(ReviewCreateResponse.ReviewDetails.builder()
                        .reviewId(review.getId())
                        .reviewDate(review.getReviewDate())
                        .score(review.getScore())
                        .userId(review.getUser().getId())
                        .orderItemId(review.getOrderItem().getId())
                        .accommodationId(review.getOrderItem().getProduct().getAccommodation().getId())
                        .productId(review.getProduct().getId())
                        .content(review.getContent())
                        .build())
                .build();
    }
}