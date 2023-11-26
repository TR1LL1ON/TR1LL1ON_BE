package com.ybe.tr1ll1on.domain.review.controller;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewCreateResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewUpdateResponse;
import com.ybe.tr1ll1on.domain.review.service.ReviewService;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewListResponse;
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

    @GetMapping
    public ResponseEntity<List<ReviewListResponse>> getReviews() {
        List<ReviewListResponse> reviewListResponse = reviewService.getAllReviews();
        return ResponseEntity.ok(reviewListResponse);
    }

    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest) {
        ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest);
        return ResponseEntity.ok(reviewCreateResponse);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        ReviewUpdateResponse reviewUpdateResponse = reviewService.updateReview(reviewId, reviewUpdateRequest);
        return ResponseEntity.ok(reviewUpdateResponse);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
