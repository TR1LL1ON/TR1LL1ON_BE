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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserReviewListResponse {

    private Long reviewId;
    private LocalDate reviewDate;
    private double score;
    private Long orderItemId;
    private Long accommodationId;
    private Long productId;
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