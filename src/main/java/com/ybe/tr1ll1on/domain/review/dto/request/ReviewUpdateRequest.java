package com.ybe.tr1ll1on.domain.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewUpdateRequest {

    private String comment;
    private double rating;
}
