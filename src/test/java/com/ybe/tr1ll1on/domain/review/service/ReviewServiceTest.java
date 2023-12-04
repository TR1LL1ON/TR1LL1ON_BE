package com.ybe.tr1ll1on.domain.review.service;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
import com.ybe.tr1ll1on.domain.review.dto.response.ProductReviewListResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.ReviewCreateResponse;
import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.domain.review.repository.ReviewRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtilImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ybe.tr1ll1on.security.common.Authority.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@WithMockUser(username = "testUser", roles = "USER")
public class ReviewServiceTest {

    @Mock
    private SecurityUtilImpl securityUtil;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Long userId;
    private Long productId;
    private Long accommodationId;
    private User testUser;
    private Product testProduct;
    private Accommodation testAccommodation;

    @BeforeEach
    void setUp() {
        userId = 1L;
        when(securityUtil.getCurrentUserId()).thenReturn(userId);

        testUser = User.builder()
                .name("user A")
                .password("password A")
                .email("userA@email.com")
                .authority(ROLE_USER)
                .build();
        testUser.setId(userId);
        when(userRepository.getUserById(userId)).thenReturn(testUser);

        accommodationId = 1L;

        testAccommodation = new Accommodation();
        testAccommodation.setId(accommodationId);
        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(testAccommodation));

        productId = 1L;
        testProduct = Product.builder()
                .name("product A")
                .checkInTime("2023-01-01T10:00:00")
                .checkOutTime("2023-01-01T14:00:00")
                .standardNumber(2)
                .maximumNumber(4)
                .count(10)
                .build();
        testProduct.setId(productId);
        testProduct.setAccommodation(testAccommodation);

        testAccommodation.getProductList().add(testProduct);

        Review testReview1 = Review.builder()
                .id(1L)
                .reviewDate(LocalDate.now())
                .score(4.5)
                .content("굿")
                .build();
        testReview1.setUser(testUser);
        testReview1.setProduct(testProduct);

        Review testReview2 = Review.builder()
                .id(2L)
                .reviewDate(LocalDate.now())
                .score(4.5)
                .content("굿")
                .build();
        testReview2.setUser(testUser);
        testReview2.setProduct(testProduct);

        testProduct.getReviewList().add(testReview1);
        testProduct.getReviewList().add(testReview2);
    }

    @Test
    void getProductReviewsTest() {

        List<ProductReviewListResponse> expectedResponse = new ArrayList<>();

        expectedResponse.add(ProductReviewListResponse.builder()
                .reviewId(1L)
                .score(5.0)
                .userId(userId)
                .reviewDate(LocalDate.now())
                .productId(productId)
                .content("Good!")
                .build());

        expectedResponse.add(ProductReviewListResponse.builder()
                .reviewId(2L)
                .score(4.5)
                .userId(userId)
                .reviewDate(LocalDate.now())
                .productId(productId)
                .content("Good!")
                .build());

        List<ProductReviewListResponse> actualResponse = reviewService.getProductReviews(accommodationId);

        assertEquals(expectedResponse.size(), actualResponse.size());
    }


    @Test
    public void testCreateReview() {
        // when

        // 테스트 주문 아이템
        Long orderItemId = 1L;
        OrderItem testOrderItem = OrderItem.builder()
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2023-01-02"))
                .personNumber(2)
                .price(100000)
                .reviewWritten(false)
                .build();
        testOrderItem.setId(orderItemId);
        testOrderItem.setProduct(testProduct);

        // 요청
        ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.builder()
                .orderItemId(orderItemId)
                .score(5)
                .content("Good!")
                .build();

        // 응답
        Long reviewId = 1L;
        ReviewCreateResponse.ReviewDetails reviewDetails = ReviewCreateResponse.ReviewDetails.builder()
                .reviewId(reviewId)
                .reviewDate(LocalDate.parse("2023-01-05"))
                .score(5)
                .userId(userId)
                .orderItemId(orderItemId)
                .accommodationId(accommodationId)
                .productId(productId)
                .content("Good.")
                .build();

        ReviewCreateResponse reviewCreateResponse = ReviewCreateResponse.builder()
                .message("리뷰가 성공적으로 작성되었습니다.")
                .review(reviewDetails)
                .build();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(testOrderItem));

        when(reviewRepository.save(Mockito.any())).thenAnswer(invocation -> {
            Review review = invocation.getArgument(0);
            return review;
        });

        // then
        ReviewCreateResponse reviewCreateResult = reviewService.createReview(reviewCreateRequest);

        // given
        assertTrue(testOrderItem.getReviewWritten());

    }
}