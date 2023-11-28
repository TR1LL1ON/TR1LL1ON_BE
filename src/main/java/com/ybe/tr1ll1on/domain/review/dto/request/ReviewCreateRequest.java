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
    private double score;
    private String content;

    public Review toEntity() {
        return Review.builder()
                .score(score)
                .content(content)
                .reviewDate(LocalDate.now())
                .build();
    }
}
