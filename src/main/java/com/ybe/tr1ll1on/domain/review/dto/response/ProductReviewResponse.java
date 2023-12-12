package com.ybe.tr1ll1on.domain.review.dto.response;

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
public class ProductReviewResponse {
    private Long reviewId;
    private LocalDate reviewDate;
    private double score;
    private String content;

    private UserDetailsResponse userDetails;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDetailsResponse {
        private Long userId;
        private String userName;

        public static UserDetailsResponse fromEntity(Review review) {

            return UserDetailsResponse.builder()
                    .userId(review.getUser().getId())
                    .userName(review.getUser().getName())
                    .build();
        }
    }

    public static ProductReviewResponse fromEntity(Review review) {

        return ProductReviewResponse.builder()
                .reviewId(review.getId())
                .userDetails(UserDetailsResponse.fromEntity(review))
                .reviewDate(review.getReviewDate())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}