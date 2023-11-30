package com.ybe.tr1ll1on.domain.review.dto.response;

import com.ybe.tr1ll1on.domain.review.model.Review;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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

    @Schema(example = "리뷰가 성공적으로 수정되었습니다.")
    private String message;

    @ArraySchema(schema = @Schema(implementation = ReviewDetails.class))
    private ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDetails {

        @NotNull(message = "12")
        private Long reviewId;

        @NotNull(message = "2023-11-28")
        private LocalDate updateDate; // 수정 날짜

        @NotNull(message = "5.0")
        private double score;

        @NotNull(message = "34")
        private Long userId;

        @NotNull(message = "56")
        private Long orderItemId;

        @NotNull(message = "78")
        private Long accommodationId;

        @NotNull(message = "90")
        private Long productId;

        @NotNull(message = "대체적으로 만족합니다! 조식 구성이 다양했으면 좋겠어요!")
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