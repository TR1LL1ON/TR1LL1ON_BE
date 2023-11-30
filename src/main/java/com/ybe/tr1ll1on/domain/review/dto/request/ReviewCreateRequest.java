package com.ybe.tr1ll1on.domain.review.dto.request;

import com.ybe.tr1ll1on.domain.review.model.Review;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "56")
    private Long orderItemId;

    @Schema(example = "4.5")
    private double score;

    @Schema(example = "대체적으로 만족합니다!")
    private String content;

    public Review toEntity() {
        return Review.builder()
                .score(score)
                .content(content)
                .reviewDate(LocalDate.now())
                .build();
    }
}
