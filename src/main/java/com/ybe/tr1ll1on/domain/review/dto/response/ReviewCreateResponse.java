package com.ybe.tr1ll1on.domain.review.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.request.OrderItemRequest;
import com.ybe.tr1ll1on.domain.review.model.Review;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "리뷰가 성공적으로 작성되었습니다.")
    private String message;

    @ArraySchema(schema = @Schema(implementation = ReviewDetails.class))
    private ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDetails {

        @Schema(example = "12")
        private Long reviewId;

        @Schema(example = "2023-11-27")
        private LocalDate reviewDate;

        @Schema(example = "4.5")
        private double score;

        @Schema(example = "34")
        private Long userId;

        @Schema(example = "56")
        private Long orderItemId;

        @Schema(example = "78")
        private Long accommodationId;

        @Schema(example = "90")
        private Long productId;

        @Schema(example = "대체적으로 만족합니다!")
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