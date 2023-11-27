package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.order.exception.OrderItemNotFoundException;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.domain.review.exception.AccommodationNotFoundException;
import com.ybe.tr1ll1on.domain.review.exception.ReviewAlreadyWrittenException;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.domain.review.exception.ReviewExceptionCode;
import com.ybe.tr1ll1on.domain.review.exception.ReviewNotFoundException;
import com.ybe.tr1ll1on.domain.review.repository.ReviewRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    /**
     * 상품에 대한 리뷰 목록을 조회
     *
     * @param accommodationId 상품 ID
     * @return 상품에 대한 리뷰 목록
     */
    @Transactional
    public List<ProductReviewListResponse> getProductReviews(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new AccommodationNotFoundException(ReviewExceptionCode.ACCOMMODATION_NOT_FOUND));

        List<Product> Products = accommodation.getProductList();
        List<ProductReviewListResponse> productReviewListResponse = new ArrayList<>();

        for (Product product : Products) {
            List<Review> allReviews = product.getReviewList();
            productReviewListResponse.addAll(allReviews.stream()
                    .map(ProductReviewListResponse::fromEntity)
                    .collect(Collectors.toList()));
        }
        return productReviewListResponse;
    }

    /**
     * 현재 사용자의 리뷰 목록을 조회
     *
     * @return 현재 사용자의 리뷰 목록
     */
    @Transactional
    public List<UserReviewListResponse> getUserReviews() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.getUserById(userId);

        List<Review> reviews = reviewRepository.findReviewsByUser(user);

        return reviews.stream()
                .map(UserReviewListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰를 생성합니다.
     *
     * @param reviewCreateRequest 리뷰 생성 요청 객체
     * @return 생성된 리뷰의 응답 객체
     */
    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        // 사용자 정보
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.getUserById(userId);

        // 상품 정보
        Long orderItemId = reviewCreateRequest.getOrderItemId();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException(ReviewExceptionCode.ORDER_ITEM_NOT_FOUND));

        if (orderItem.getReviewWritten()) {
            throw new ReviewAlreadyWrittenException(ReviewExceptionCode.REVIEW_CONFLICT);
        }

        Product product = orderItem.getProduct();

        // 리뷰 엔티티 생성
        Review review = reviewCreateRequest.toEntity();
        review.setOrderItem(orderItem);
        review.setUser(user);
        review.setProduct(product);

        // 리뷰 엔티티 데이터베이스 저장
        Review savedReview = reviewRepository.save(review);

        // 리뷰 작성 여부 true
        orderItem.setReviewWritten(true);

        return ReviewCreateResponse.fromEntity(savedReview);
    }

    /**
     * 리뷰를 수정합니다.
     *
     * @param reviewId 리뷰 ID
     * @param reviewUpdateRequest 리뷰 수정 요청 객체
     * @return 수정된 리뷰의 응답 객체
     */
    @Transactional
    public ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        // 리뷰 정보
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ReviewExceptionCode.REVIEW_NOT_FOUND));

        // 리뷰 수정
        review.update(reviewUpdateRequest);

        // 수정된 리뷰 데이터베이스 업데이트
        Review updatedReview = reviewRepository.save(review);

        return ReviewUpdateResponse.fromEntity(updatedReview);
    }

    /**
     * 리뷰를 삭제합니다.
     *
     * @param reviewId 리뷰 ID
     * @return 삭제된 리뷰의 응답 객체
     */
    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        // 리뷰 정보
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(ReviewExceptionCode.REVIEW_NOT_FOUND));

        // 데이터베이스에서 리뷰 삭제. 재작성 불가. ReviewWritten true 유지.
        reviewRepository.delete(review);

        return ReviewDeleteResponse.fromEntity(review);
    }
}