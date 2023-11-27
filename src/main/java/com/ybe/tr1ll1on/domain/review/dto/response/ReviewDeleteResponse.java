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
public class ReviewDeleteResponse {

    String message;
    ReviewDetails review;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReviewDetails {

        private Long reviewId;
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
