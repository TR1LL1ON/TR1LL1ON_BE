package com.ybe.tr1ll1on.domain.review.service;

import static com.ybe.tr1ll1on.domain.review.exception.ReviewExceptionCode.*;

import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode;
import com.ybe.tr1ll1on.domain.review.dto.response.*;
import com.ybe.tr1ll1on.domain.review.exception.ReviewException;
import com.ybe.tr1ll1on.domain.review.exception.ReviewExceptionCode;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.domain.review.repository.ReviewRepository;
import com.ybe.tr1ll1on.global.common.ReviewPeriod;
import com.ybe.tr1ll1on.global.common.ReviewStatus;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;

    /* 숙소 전체 리뷰 조회 */
    @Transactional
    public Page<ProductAllReviewResponse> getProductAllReviews(Long accommodationId, Pageable pageable) {
        pageable = addSort(pageable);
        Page<Review> reviewPage = reviewRepository.getReviewPageByAccommodationWithDetails(
                accommodationId, pageable
        );

        List<ProductAllReviewResponse> productAllReviewResponseList = reviewPage.getContent().stream()
                .map(ProductAllReviewResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(productAllReviewResponseList, pageable, reviewPage.getTotalElements());
    }

    /* 특정 상품 리뷰 조회 */
    @Transactional
    public List<ProductReviewResponse> getProductReviews(Long productId) {
        List<Review> reviewList = getReviewListByProductId(productId);

        List<ProductReviewResponse> productReviewResponseList = reviewList.stream()
                .map(ProductReviewResponse::fromEntity)
                .collect(Collectors.toList());

        return productReviewResponseList;
    }

    /* 사용자 전체 리뷰 조회 */
    @Transactional
    public Page<UserReviewResponse> getUserAllReviews(ReviewPeriod reviewPeriod, Pageable pageable) {
        User user = getUser();

        LocalDate startDate = calculateStartDate(reviewPeriod);

        pageable = addSort(pageable);
        Page<Review> reviewPage = reviewRepository.getReviewPageByUserWithDetailsAndDateRange(
                user, startDate, pageable
        );

        List<UserReviewResponse> userReviewResponseList = reviewPage.getContent().stream()
                .map(UserReviewResponse::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(userReviewResponseList, pageable, reviewPage.getTotalElements());
    }

    /* 사용자 특정 리뷰 조회 */
    @Transactional
    public UserReviewResponse getUserReview(Long reviewId) {
        Review review = getReview(reviewId);

        return UserReviewResponse.fromEntity(review);
    }

    /* 리뷰 작성 */
    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest reviewCreateRequest) {
        User user = getUser();
        OrderItem orderItem = getOrderItem(reviewCreateRequest.getOrderItemId());
        validateReviewStatus(orderItem);

        Product product = orderItem.getProduct();

        Review review = reviewCreateRequest.toEntity();
        review.setOrderItem(orderItem);
        review.setUser(user);
        review.setProduct(product);

        Review savedReview = reviewRepository.save(review);
        if (savedReview == null) {
            throw new ReviewException(ReviewExceptionCode.REVIEW_SAVE_FAILED);
        }

        orderItem.setReviewStatus(ReviewStatus.WRITTEN);

        return ReviewCreateResponse.fromEntity(savedReview);
    }

    /* 리뷰 수정 */
    @Transactional
    public ReviewUpdateResponse updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = getReview(reviewId);

        review.update(reviewUpdateRequest);

        Review updatedReview = reviewRepository.save(review);
        if (updatedReview == null) {
            throw new ReviewException(ReviewExceptionCode.REVIEW_SAVE_FAILED);
        }

        return ReviewUpdateResponse.fromEntity(updatedReview);
    }

    /* 리뷰 삭제 */
    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        Review review = getReview(reviewId);

        OrderItem orderItem = getOrderItem(review.getOrderItem().getId());

        reviewRepository.delete(review);

        orderItem.setReviewStatus(ReviewStatus.DELETED);

        return ReviewDeleteResponse.fromEntity(review);
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(InValidUserExceptionCode.USER_NOT_FOUND));
    }

    private List<Review> getReviewListByProductId(Long productId) {
        return reviewRepository.getReviewListByProductId(productId)
                .orElseThrow(() -> new ProductException(ProductExceptionCode.EMPTY_PRODUCT));
    }

    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
    }

    private OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderException(OrderExceptionCode.ORDER_ITEM_NOT_FOUND));
    }

    private Pageable addSort(Pageable pageable) {
        Sort addSort = Sort.by(Sort.Order.desc("id"));
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(addSort)
        );
    }

    private LocalDate calculateStartDate(ReviewPeriod period) {
        return LocalDate.now().minusMonths(period.getNumOfPeriod());
    }

    private void validateReviewStatus(OrderItem orderItem) {
        checkReviewAlreadyExists(orderItem);
        checkReviewAlreadyDeleted(orderItem);
    }

    private void checkReviewAlreadyExists(OrderItem orderItem) {
        if (orderItem.getReviewStatus() == ReviewStatus.WRITTEN) {
            throw new ReviewException(REVIEW_ALREADY_EXISTS);
        }
    }

    private void checkReviewAlreadyDeleted(OrderItem orderItem) {
        if (orderItem.getReviewStatus() == ReviewStatus.DELETED) {
            throw new ReviewException(REVIEW_ALREADY_DELETED);
        }
    }
}
