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
public class ReviewUpdateResponse {

    private String message;
    private ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReviewDetails {

        private Long reviewId;
        private LocalDate updateDate; // 수정 날짜
        private double score;
        private Long userId;
        private Long orderItemId;
        private Long accommodationId;
        private Long productId;
        private String content;

    }

    public static ReviewUpdateResponse fromEntity(Review review) {
        return ReviewUpdateResponse.builder()
                .message("리뷰가 성공적으로 수정되었습니다.")
                .review(ReviewDetails.builder()
                        .reviewId(review.getId())
                        .updateDate(LocalDate.now())
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