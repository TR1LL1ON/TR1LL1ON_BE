package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.ProductReviewListResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewCreateResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewDeleteResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewUpdateResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.UserReviewListResponse;
import java.util.List;

public interface ReviewService {
    List<ProductReviewListResponse> getProductReviews(Long accommodationId);
    List<UserReviewListResponse> getUserReviews();
    ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest);
    ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest);
    ReviewDeleteResponse deleteReview(Long reviewId);
}
