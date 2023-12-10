package com.ybe.tr1ll1on.domain.review.service;

import static com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationExceptionCode.ACCOMMODATION_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.review.exception.ReviewExceptionCode.*;

import com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationException;
import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
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
import com.ybe.tr1ll1on.global.common.ReviewStatus;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    /**
     * 상품에 대한 리뷰 목록을 조회
     *
     * @param accommodationId 숙소 ID
     * @return 특정 숙소의 상품에 대한 리뷰 목록
     */
    @Transactional
    public Page<ProductAllReviewResponse> getProductAllReviews(Long accommodationId, Pageable pageable) {
        // 1. 특정 숙소에 대한 정보를 가져오면서 관련된 데이터를 패치 조인을 통해 즉시 로딩한다.
        // 2. 관련된 데이터엔 상품 목록이 포함되어 있다.
        // 3. ※ 주의 - 즉시 로딩 대상인 엔티티와 연관관계인 엔티티가 eager type 일 경우 함께 즉시 로딩된다.
        Accommodation accommodation = getAccommodation(accommodationId);

        // 1. 특정 숙소에 대한 정보는 위에서 즉시 로딩된 상태다.
        // 2. 영속성 컨텍스트에서 이미 해당 데이터를 가지고 있어 추가로 데이터베이스에 접근하지 않는다.
        // 3. 그렇기 때문에 accommodation 엔티티 접근 시 추가 쿼리가 발생하지 않는다.
        List<Product> products = accommodation.getProductList();
        List<ProductAllReviewResponse> productReviewResponseList = new ArrayList<>();

        // 특정 숙소에 대한 상품 목록도 마찬가지로 영속성 컨텍스트에 이미 저장된 상태이다.
        for (Product product : products) {
            List<Review> reviews = product.getReviewList();

            // 1. 특정 상품의 리뷰 리스트에 대한 batch size = 100 으로 설정한 상태이다.
            //    - 이후 리뷰 엔티티 접근 시 지정한 개수만큼 상품 아이디에 해당하는 리뷰 즉시 로딩
            // 2. 특정 상품의 이미지 리스트에 대한 batch size = 100 으로 설정한 상태이다.
            //    - 이후 상품 이미지 엔티티 접근 시 지정한 개수만큼 상품 아이디에 해당하는 이미지 즉시 로딩
            // 3. 리뷰와 관련된 상품 정보는 이미 로딩된 상태이므로 추가 쿼리가 발생하지 않는다.
            productReviewResponseList.addAll(reviews.stream()
                    .map(ProductAllReviewResponse::fromEntity)
                    .collect(Collectors.toList()));
        }

        // 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productReviewResponseList.size());

        return new PageImpl<>(productReviewResponseList.subList(start, end), pageable, productReviewResponseList.size());
    }

    /**
     *
     * @param productId 상품 ID
     * @return 특정 상품에 대한 리뷰 목록
     */
    @Transactional
    public List<ProductReviewResponse> getProductReviews(Long productId) {
        List<Review> reviews = getReviewsByProductId(productId);

        List<ProductReviewResponse> productReviewResponseList = reviews.stream()
                .map(ProductReviewResponse::fromEntity)
                .collect(Collectors.toList());

        return productReviewResponseList;
    }


    /**
     * 현재 사용자의 전체 리뷰 목록을 조회
     *
     * @return 현재 사용자의 리뷰 목록
     */
    @Transactional
    public Page<UserReviewResponse> getUserReviews(Pageable pageable) {
        User user = getUser();

        // 특정 사용자의 리뷰에 대한 정보를 가져오면서 관련된 데이터를 패치 조인을 통해 즉시 로딩한다.
        // 관련된 데이터엔 숙소, 상품, 주문 상품에 대한 정보가 포함되어 있다.
        // ※ 주의 - 즉시 로딩 대상인 엔티티와 연관관계인 엔티티가 eager type 일 경우 함께 즉시 로딩된다.
        Page<Review> reviewPage = reviewRepository.getReviewsByUserWithDetails(user, pageable);

        // 특정 상품의 이미지 리스트에 대한 batch size = 100으로 설정한 상태이다.
        // 이후 상품 이미지 엔티티 접근 시 지정한 개수만큼 상품 아이디에 해당하는 상품 이미지 즉시 로딩
        List<UserReviewResponse> userReviewResponseList = reviewPage.getContent().stream()
                .map(UserReviewResponse::fromEntity)
                .collect(Collectors.toList());

        // PageImpl을 사용하여 Page 객체 생성
        return new PageImpl<>(userReviewResponseList, pageable, reviewPage.getTotalElements());
    }

    /**
     * 현재 사용자의 특정 리뷰를 조회
     *
     * @param reviewId 리뷰 ID
     * @return
     */
    @Transactional
    public UserReviewResponse getUserReview(Long reviewId) {
        Review review = getReview(reviewId);

        return UserReviewResponse.fromEntity(review);
    }

    /**
     * 리뷰를 생성합니다.
     *
     * @param reviewCreateRequest 리뷰 생성 요청 객체
     * @return 생성된 리뷰의 응답 객체
     */
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


    /**
     * 리뷰를 수정합니다.
     *
     * @param reviewId 리뷰 ID
     * @param reviewUpdateRequest 리뷰 수정 요청 객체
     * @return 수정된 리뷰의 응답 객체
     */
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

    /**
     * 리뷰를 삭제합니다.
     *
     * @param reviewId 리뷰 ID
     * @return 삭제된 리뷰의 응답 객체
     */
    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        Review review = getReview(reviewId);

        OrderItem orderItem = getOrderItem(review.getOrderItem().getId());

        reviewRepository.delete(review);

        orderItem.setReviewStatus(ReviewStatus.DELETED);

        return ReviewDeleteResponse.fromEntity(review);
    }

    private Accommodation getAccommodation(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.getAccommodationWithProductsById(accommodationId)
                .orElseThrow(() -> new AccommodationException(ACCOMMODATION_NOT_FOUND));
        return accommodation;
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(InValidUserExceptionCode.USER_NOT_FOUND));
    }

    private List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.getReviewsByProductId(productId)
                .orElseThrow(() -> new ProductException(ProductExceptionCode.EMPTY_PRODUCT));
    }

    private Review getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
        return review;
    }

    private OrderItem getOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderException(OrderExceptionCode.ORDER_ITEM_NOT_FOUND));
        return orderItem;
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