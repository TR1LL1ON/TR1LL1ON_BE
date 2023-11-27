package com.ybe.tr1ll1on.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.domain.review.controller.ReviewController;
import com.ybe.tr1ll1on.domain.review.dto.response.ProductReviewListResponse;
import com.ybe.tr1ll1on.domain.review.dto.response.UserReviewListResponse;
import com.ybe.tr1ll1on.domain.review.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testUser", roles = "USER")
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetProductReviews() throws Exception {
        // Mock 데이터 생성
        List<ProductReviewListResponse> mockResponse = Arrays.asList(
                ProductReviewListResponse.builder()
                        .reviewId(1L)
                        .reviewDate(LocalDate.parse("2023-11-30"))
                        .score(4.5)
                        .userId(101L)
                        .productId(401L)
                        .content("굿!")
                        .build()
        );
        Long accommodationId = 1L;

        // Mock Service 호출을 설정
        when(reviewService.getProductReviews(accommodationId)).thenReturn(mockResponse);

        // GET 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{accommodationId}", accommodationId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_date").value("2023-11-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user_id").value(101L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_id").value(401L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("굿!"));
    }

    @Test
    public void testGetUserReviews() throws Exception {
        // Mock 데이터 생성
        List<UserReviewListResponse> mockResponse = Arrays.asList(
                UserReviewListResponse.builder()
                        .reviewId(1L)
                        .reviewDate(LocalDate.parse("2023-11-30"))
                        .score(4.5)
                        .orderItemId(201L)
                        .accommodationId(301L)
                        .productId(401L)
                        .content("굿!")
                        .build()
        );

        // Mock Service 호출을 설정
        when(reviewService.getUserReviews()).thenReturn(mockResponse);

        // GET 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review_date").value("2023-11-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(4.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].order_item_id").value(201L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].accommodation_id").value(301L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_id").value(401L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("굿!"));
    }
}
