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
public class ProductReviewListResponse {

    private Long reviewId;
    private LocalDate reviewDate;
    private double score;
    private Long userId;
    private Long productId;
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