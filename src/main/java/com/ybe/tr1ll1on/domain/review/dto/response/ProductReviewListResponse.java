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
public class ProductReviewListResponse {

    @Schema(example = "12")
    private Long reviewId;

    @Schema(example = "2023-11-26")
    private LocalDate reviewDate;

    @Schema(example = "5.0")
    private double score;

    @Schema(example = "34")
    private Long userId;

    @Schema(example = "90")
    private Long productId;

    @Schema(example = "매우 만족합니다. 청결하네요!")
    private String content;

    public static ProductReviewListResponse fromEntity(Review review) {
        return ProductReviewListResponse.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .userId(review.getUser().getId())
                .reviewDate(review.getReviewDate())
                .productId(review.getProduct().getId())
                .content(review.getContent())
                .build();
    }
}