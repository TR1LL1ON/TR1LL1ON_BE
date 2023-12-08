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

        @Schema(example = "12")
        private Long reviewId;
        @Schema(example = "2023-11-28")
        private LocalDate updateDate; // 수정 날짜
        @Schema(example = "5.0")
        private double score;
        @Schema(example = "대체적으로 만족합니다! 조식 구성이 다양했으면 좋겠어요!")
        private String content;

        public static ReviewDetails fromEntity(Review review) {

            return ReviewDetails.builder()
                    .reviewId(review.getId())
                    .updateDate(LocalDate.now())
                    .score(review.getScore())
                    .content(review.getContent())
                    .build();
        }
    }

    public static ReviewUpdateResponse fromEntity(Review review) {

        return ReviewUpdateResponse.builder()
                .message("리뷰가 성공적으로 수정되었습니다.")
                .review(ReviewDetails.fromEntity(review))
                .build();
    }
}