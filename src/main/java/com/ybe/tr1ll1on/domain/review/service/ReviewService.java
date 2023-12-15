package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.global.common.ReviewPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<ProductAllReviewResponse> getProductAllReviews(Long accommodationId, Pageable pageable);
    List<ProductReviewResponse> getProductReviews(Long productId);
    Page<UserReviewResponse> getUserAllReviews(ReviewPeriod reviewPeriod, Pageable pageable);
    UserReviewResponse getUserReview(Long reviewId);
    ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest);
    ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest);
    ReviewDeleteResponse deleteReview(Long reviewId);
}
