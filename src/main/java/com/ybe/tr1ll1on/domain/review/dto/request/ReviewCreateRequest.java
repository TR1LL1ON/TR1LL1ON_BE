package com.ybe.tr1ll1on.domain.review.dto.request;

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
public class ReviewCreateRequest {

    private Long orderItemId;
    private String comment;
    private int rating;

    public Review toEntity() {
        return Review.builder()
                .comment(comment)
                .rating(rating)
                .reviewDate(LocalDate.now())
                .build();
    }
}
