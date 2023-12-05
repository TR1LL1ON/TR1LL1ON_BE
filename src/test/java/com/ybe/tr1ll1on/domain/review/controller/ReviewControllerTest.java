//package com.ybe.tr1ll1on.domain.review.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ybe.tr1ll1on.domain.review.dto.request.ReviewCreateRequest;
//import com.ybe.tr1ll1on.domain.review.dto.request.ReviewUpdateRequest;
//import com.ybe.tr1ll1on.domain.review.dto.response.*;
//import com.ybe.tr1ll1on.domain.review.model.Review;
//import com.ybe.tr1ll1on.domain.review.service.ReviewService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser(username = "testUser", roles = "USER")
//public class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @InjectMocks
//    private ReviewController reviewController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testGetProductReviews() throws Exception {
//        // Mock 데이터 생성
//        List<ProductReviewListResponse> mockResponse = Arrays.asList(
//                ProductReviewListResponse.builder()
//                        .reviewId(1L)
//                        .reviewDate(LocalDate.parse("2023-11-30"))
//                        .score(4.5)
//                        .userId(101L)
//                        .productId(401L)
//                        .content("굿!")
//                        .build()
//        );
//        Long accommodationId = 1L;
//
//        // Mock Service 호출을 설정
//        when(reviewService.getProductReviews(accommodationId)).thenReturn(mockResponse);
//
//        // GET 요청 및 응답 검증
//        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{accommodationId}", accommodationId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_id").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_date").value("2023-11-30"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(4.5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user_id").value(101L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_id").value(401L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("굿!"));
//    }
//
//    @Test
//    public void testGetUserReviews() throws Exception {
//        // Mock 데이터 생성
//        List<UserReviewListResponse> mockResponse = Arrays.asList(
//                UserReviewListResponse.builder()
//                        .reviewId(1L)
//                        .reviewDate(LocalDate.parse("2023-11-30"))
//                        .score(4.5)
//                        .orderItemId(201L)
//                        .accommodationId(301L)
//                        .productId(401L)
//                        .content("굿!")
//                        .build()
//        );
//
//        // Mock Service 호출을 설정
//        when(reviewService.getUserReviews()).thenReturn(mockResponse);
//
//        // GET 요청 및 응답 검증
//        mockMvc.perform(MockMvcRequestBuilders.get("/reviews")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_id").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_date").value("2023-11-30"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(4.5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].order_item_id").value(201L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].accommodation_id").value(301L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_id").value(401L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("굿!"));
//    }
//
//    @Test
//    public void testCreateReview() throws Exception {
//        // Mock 데이터 생성
//        ReviewCreateRequest createRequest = ReviewCreateRequest.builder()
//                .orderItemId(1L)
//                .score(4.5)
//                .content("Good review")
//                .build();
//
//        Review mockReview = Review.builder()
//                .id(1L)
//                .score(createRequest.getScore())
//                .content(createRequest.getContent())
//                .reviewDate(LocalDate.now())
//                .build();
//
//        ReviewCreateResponse mockResponse = ReviewCreateResponse.fromEntity(mockReview);
//
//        // Mock Service 호출을 설정
//        when(reviewService.createReview(any(ReviewCreateRequest.class))).thenReturn(mockResponse);
//
//        // POST 요청 및 응답 검증
//        mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createRequest)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("리뷰가 성공적으로 작성되었습니다."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.reviewId").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.score").value(4.5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.userId").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.orderItemId").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.accommodationId").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.productId").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.content").value("굿"));
//    }
//
//    @Test
//    public void testUpdateReview() throws Exception {
//        // Mock 데이터 생성
//        Long reviewId = 1L;
//        ReviewUpdateRequest updateRequest = ReviewUpdateRequest.builder()
//                .score(4.0)
//                .content("굿")
//                .build();
//
//        Review mockReview = Review.builder()
//                .id(reviewId)
//                .score(updateRequest.getScore())
//                .content(updateRequest.getContent())
//                .build();
//
//        ReviewUpdateResponse mockResponse = ReviewUpdateResponse.fromEntity(mockReview);
//
//        // Mock Service 호출을 설정
//        when(reviewService.updateReview(reviewId, updateRequest)).thenReturn(mockResponse);
//
//        // PUT 요청 및 응답 검증
//        mockMvc.perform(MockMvcRequestBuilders.put("/reviews/{reviewId}", reviewId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("리뷰가 성공적으로 수정되었습니다."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.reviewId").value(reviewId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.updateDate").value(LocalDate.now().toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.score").value(4.0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.content").value("굿"));
//    }
//
//    @Test
//    public void testDeleteReview() throws Exception {
//        // Mock 데이터
//        Long reviewId = 1L;
//        Review mockReview = Review.builder()
//                .id(reviewId)
//                .build();
//
//        ReviewDeleteResponse mockResponse = ReviewDeleteResponse.fromEntity(mockReview);
//
//        // Mock 서비스 호출
//        when(reviewService.deleteReview(reviewId)).thenReturn(mockResponse);
//
//        // DELETE 요청 및 응답 검증
//        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/{reviewId}", reviewId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("리뷰가 성공적으로 삭제되었습니다."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.reviewId").value(reviewId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.review.deleteDate").exists());
//    }
//}
