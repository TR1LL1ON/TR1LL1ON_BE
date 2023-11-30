package com.ybe.tr1ll1on.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewUpdateRequest {

    @Schema(example = "5.0")
    private double score;

    @Schema(example = "대체적으로 만족합니다! 조식 구성이 다양했으면 좋겠어요!")
    private String content;
}
