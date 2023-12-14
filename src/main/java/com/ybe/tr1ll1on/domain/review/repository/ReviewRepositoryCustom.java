package com.ybe.tr1ll1on.domain.review.repository;

import com.ybe.tr1ll1on.domain.review.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

public interface ReviewRepositoryCustom {
    Slice<Review> getProductAllReviews(
            Long accommodationId,
            Double cursorScore, LocalDate cursorReviewDate, Long cursorReviewId,
            Pageable pageable
    );
}

