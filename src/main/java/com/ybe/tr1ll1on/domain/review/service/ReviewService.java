package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.order.exception.OrderItemNotFoundException;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewCreateResponse;
import com.ybe.tr1ll1on.domain.review.exception.ReviewAlreadyWrittenException;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewListResponse;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import com.ybe.tr1ll1on.domain.review.exception.ReviewNotFoundException;
import com.ybe.tr1ll1on.domain.review.repository.ReviewRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<ReviewListResponse> getAllReviews() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.getUserById(userId);

        List<Review> reviews = reviewRepository.findReviewsByUser(user);

        return reviews.stream()
                .map(ReviewListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        // 사용자 정보
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.getUserById(userId);

        // 상품 정보
        Long orderItemId = reviewCreateRequest.getOrderItemId();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException(TrillionExceptionCode.ORDER_ITEM_NOT_FOUND));

        if (orderItem.getReviewWritten()) {
            throw new ReviewAlreadyWrittenException(TrillionExceptionCode.REVIEW_CONFLICT);
        }

        Product product = orderItem.getProduct();

        // 리뷰 엔티티 생성
        Review review = reviewCreateRequest.toEntity();
        review.setUser(user);
        review.setProduct(product);

        // 리뷰 엔티티 데이터베이스 저장
        Review savedReview = reviewRepository.save(review);

        // 리뷰 작성 여부 true
        orderItem.setReviewWritten(true);

        return ReviewCreateResponse.fromEntity(savedReview);
    }

    @Transactional
    public ResponseEntity<String> updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        System.out.println(reviewId);

        // 리뷰 정보
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(TrillionExceptionCode.REVIEW_NOT_FOUND));

        // 리뷰 수정
        review.update(reviewUpdateRequest);

        // 수정된 리뷰 데이터베이스 업데이트
        Review updatedReview = reviewRepository.save(review);

        return ResponseEntity.ok("리뷰 수정이 완료되었습니다.");
    }

    @Transactional
    public ResponseEntity<String> deleteReview(Long reviewId) {
        // 리뷰 정보
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(TrillionExceptionCode.REVIEW_NOT_FOUND));

        // 데이터베이스에서 리뷰 삭제. 재작성 불가. ReviewWritten true 유지.
        reviewRepository.delete(review);

        return ResponseEntity.ok("리뷰 삭제가 완료되었습니다.");
    }
}
