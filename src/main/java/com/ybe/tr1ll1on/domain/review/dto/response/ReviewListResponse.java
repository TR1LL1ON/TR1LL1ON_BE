package com.ybe.tr1ll1on.domain.review.dto.response;

import com.ybe.tr1ll1on.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponse {

    private Long reviewId;
    private String comment;
    private int rating;
    private LocalDate reviewDate;

    public static ReviewListResponse fromEntity(Review review) {
        return ReviewListResponse.builder()
                .reviewId(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewDate(review.getReviewDate())
                .build();
    }
}

