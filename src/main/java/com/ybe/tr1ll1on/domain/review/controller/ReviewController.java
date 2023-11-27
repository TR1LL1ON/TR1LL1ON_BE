package com.ybe.tr1ll1on.domain.review.controller;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;


    @GetMapping("/{accommodationId}")
    public ResponseEntity<List<ProductReviewListResponse>> getProductReviews(@PathVariable Long accommodationId) {
        List<ProductReviewListResponse> productReviewListResponse = reviewService.getProductReviews(accommodationId);
        return ResponseEntity.ok(productReviewListResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserReviewListResponse>> getUserReviews() {
        List<UserReviewListResponse> userReviewListResponse = reviewService.getUserReviews();
        return ResponseEntity.ok(userReviewListResponse);
    }

    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest);
        return ResponseEntity.ok(reviewCreateResponse);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        ReviewUpdateResponse reviewUpdateResponse = reviewService.updateReview(reviewId, reviewUpdateRequest);
        return ResponseEntity.ok(reviewUpdateResponse);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId) {
        ReviewDeleteResponse reviewDeleteResponse = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(reviewDeleteResponse);
    }
}
