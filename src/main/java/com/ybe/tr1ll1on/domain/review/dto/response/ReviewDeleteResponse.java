package com.ybe.tr1ll1on.domain.review.dto.response;

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
public class ReviewDeleteResponse {

    @Schema(example = "리뷰가 성공적으로 삭제되었습니다.")
    String message;

    @ArraySchema(schema = @Schema(implementation = ReviewDetails.class))
    ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewDetails {

        @Schema(example = "12")
        private Long reviewId;

        @Schema(example = "2023-11-27")
        private LocalDate deleteDate;
        // ... 추가 가능

    }

    public static ReviewDeleteResponse fromEntity(Review review) {
        return ReviewDeleteResponse.builder()
                .message("리뷰가 성공적으로 삭제되었습니다.")
                .review(ReviewDeleteResponse.ReviewDetails.builder()
                        .reviewId(review.getId())
                        .deleteDate(LocalDate.now())
                        .build())
                .build();
    }
}
