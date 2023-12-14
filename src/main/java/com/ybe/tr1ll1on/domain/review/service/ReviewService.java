package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.global.common.ReviewPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    Slice<ProductAllReviewResponse> getProductAllReviews(Long accommodationId, Double cursorScore, LocalDate cursorReviewDate, Long reviewId, Pageable pageable);
    List<ProductReviewResponse> getProductReviews(Long productId);
    Page<UserReviewResponse> getUserReviews(Pageable pageable, ReviewPeriod reviewPeriod);
    UserReviewResponse getUserReview(Long reviewId);
    ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest);
    ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest);
    ReviewDeleteResponse deleteReview(Long reviewId);
}
