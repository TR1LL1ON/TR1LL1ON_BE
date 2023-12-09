package com.ybe.tr1ll1on.domain.review.controller;

import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "리뷰 API", description = "리뷰 관련 API 모음입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "숙소 리뷰 조회 API", description = "숙소 리뷰 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = ProductReviewResponse.class)))
    @SecurityRequirement(name = "jwt")
    @GetMapping("/{accommodationId}")
    public ResponseEntity<Page<ProductReviewResponse>> getProductReviews(
            @PathVariable Long accommodationId,
            @PageableDefault(size = 10, sort = "reviewDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductReviewResponse> productReviewListResponse = reviewService.getProductReviews(accommodationId, pageable);
        return ResponseEntity.ok(productReviewListResponse);
    }

    @Operation(summary = "내 리뷰 조회 API", description = "내 리뷰 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = UserReviewResponse.class)))
    @SecurityRequirement(name = "jwt")
    @GetMapping
    public ResponseEntity<Page<UserReviewResponse>> getUserReviews(
            @PageableDefault(size = 10, sort = "reviewDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserReviewResponse> userReviewResponse = reviewService.getUserReviews(pageable);
        return ResponseEntity.ok(userReviewResponse);
    }

    @GetMapping("/written/{reviewId}")
    public ResponseEntity<UserReviewResponse> getUserReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        UserReviewResponse userReviewResponse = reviewService.getUserReview(reviewId);
        return ResponseEntity.ok(userReviewResponse);
    }

    @Operation(summary = "내 리뷰 작성 API", description = "내 리뷰 작성 API 입니다.")
    @ApiResponse(responseCode = "201", description = "작성 성공시",
            content = @Content(schema = @Schema(implementation = ReviewCreateResponse.class)))
    @SecurityRequirement(name = "jwt")
    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(
            @Valid @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        ReviewCreateResponse reviewCreateResponse = reviewService.createReview(reviewCreateRequest);
        return ResponseEntity.ok(reviewCreateResponse);
    }

    @Operation(summary = "리뷰 수정 API", description = "리뷰 수정 API 입니다.")
    @ApiResponse(responseCode = "201", description = "수정 성공시",
            content = @Content(schema = @Schema(implementation = ReviewUpdateResponse.class)))
    @SecurityRequirement(name = "jwt")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        ReviewUpdateResponse reviewUpdateResponse = reviewService.updateReview(reviewId, reviewUpdateRequest);
        return ResponseEntity.ok(reviewUpdateResponse);
    }

    @Operation(summary = "리뷰 삭제 API", description = "리뷰 삭제 API 입니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공시",
            content = @Content(schema = @Schema(implementation = ReviewDeleteResponse.class)))
    @SecurityRequirement(name = "jwt")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
            @PathVariable Long reviewId
    ) {
        ReviewDeleteResponse reviewDeleteResponse = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(reviewDeleteResponse);
    }
}
