package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.global.common.ReviewPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ProductReviewResponse> getProductReviews(Long accommodationId, Pageable pageable);
    Page<UserReviewResponse> getUserReviews(Pageable pageable, ReviewPeriod reviewPeriod);
    UserReviewResponse getUserReview(Long reviewId);
    ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest);
    ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest);
    ReviewDeleteResponse deleteReview(Long reviewId);

}