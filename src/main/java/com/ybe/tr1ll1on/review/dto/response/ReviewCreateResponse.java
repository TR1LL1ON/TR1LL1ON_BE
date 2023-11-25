package com.ybe.tr1ll1on.review.dto.response;

import com.ybe.tr1ll1on.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateResponse {

    private Long reviewId;
    private String comment;
    private int rating;
    private LocalDate reviewDate;

    public static ReviewCreateResponse fromEntity(Review review) {
        return ReviewCreateResponse.builder()
                .reviewId(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .reviewDate(review.getReviewDate())
                .build();
    }
}
