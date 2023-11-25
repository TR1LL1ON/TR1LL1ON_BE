package com.ybe.tr1ll1on.review.controller;

import com.ybe.tr1ll1on.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.review.dto.response.ReviewCreateResponse;
import com.ybe.tr1ll1on.review.dto.response.ReviewListResponse;
import com.ybe.tr1ll1on.review.service.ReviewService;
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
        ReviewCreateResponse response = reviewService.createReview(reviewCreateRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        return reviewService.updateReview(reviewId, reviewUpdateRequest);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
