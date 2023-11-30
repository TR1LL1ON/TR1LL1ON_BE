package com.ybe.tr1ll1on.domain.review.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.review.model.Review;
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
public class UserReviewListResponse {

    @Schema(example = "12")
    private Long reviewId;

    @Schema(example = "2023-11-26")
    private LocalDate reviewDate;

    @Schema(example = "5.0")
    private double score;

    @Schema(example = "56")
    private Long orderItemId;

    @Schema(example = "56")
    private Long accommodationId;

    @Schema(example = "78")
    private Long productId;

    @Schema(example = "매우 만족합니다. 청결하네요!")
    private String content;

    public static UserReviewListResponse fromEntity(Review review) {
        return UserReviewListResponse.builder()
                .reviewId(review.getId())
                .reviewDate(review.getReviewDate())
                .score(review.getScore())
                .orderItemId(review.getOrderItem().getId())
                .accommodationId(review.getOrderItem().getProduct().getAccommodation().getId())
                .productId(review.getProduct().getId())
                .content(review.getContent())
                .build();
    }
}