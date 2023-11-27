package com.ybe.tr1ll1on.domain.product.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ybe.tr1ll1on.domain.product.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.error.ProductException;
import com.ybe.tr1ll1on.domain.product.error.ProductExceptionCode;
import com.ybe.tr1ll1on.domain.product.service.ProductService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ProductControllerTest {
    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    AccommodationRequest accommodationRequest1, accommodationRequest2;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8
            );

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("숙소 상세 조회 컨트롤러 성공")
    void getAccommodationDetail_성공() throws Exception {
        accommodationRequest1 = new AccommodationRequest();
        accommodationRequest1.setCheckIn(LocalDate.now());
        accommodationRequest1.setCheckOut(LocalDate.now().plusDays(1));
        accommodationRequest1.setPersonNumber(2);

        given(productService.getAccommodationDetail(any(), any())).willReturn(null);

        mockMvc.perform(post("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationRequest1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("숙소 상세 조회 컨트롤러 실패 (체크인,체크아웃 날짜 같음)")
    void getAccommodationDetail_실패() throws Exception {
        accommodationRequest2 = new AccommodationRequest();
        accommodationRequest2.setCheckIn(LocalDate.now());
        accommodationRequest2.setCheckOut(LocalDate.now()); //같은 날짜
        accommodationRequest2.setPersonNumber(2);

        given(productService.getAccommodationDetail(any(), any())).willReturn(null);

        mockMvc.perform(post("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationRequest2)))
                .andExpect(result -> {
                    if (result.getResolvedException() instanceof ProductException) {
                        ProductException exception = (ProductException) result.getResolvedException();
                        assertEquals(ProductExceptionCode.CHECKIN_EQUALS_CHECKOUT.getMsg(), exception.getErrorCode().getMsg());
                    }
                });
    }

    @Test
    @DisplayName("객실 상세 조회 컨트롤러 성공")
    void getProduct_성공() throws Exception {
        accommodationRequest1 = new AccommodationRequest();
        accommodationRequest1.setCheckIn(LocalDate.now());
        accommodationRequest1.setCheckOut(LocalDate.now().plusDays(1));
        accommodationRequest1.setPersonNumber(2);

        given(productService.getProduct(any(), any(), any())).willReturn(null);

        mockMvc.perform(post("/products/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationRequest1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("숙소 상세 조회 컨트롤러 실패 (체크인,체크아웃 날짜 같음)")
    void getProduct_실패() throws Exception {
        accommodationRequest2 = new AccommodationRequest();
        accommodationRequest2.setCheckIn(LocalDate.now());
        accommodationRequest2.setCheckOut(LocalDate.now()); //같은 날짜
        accommodationRequest2.setPersonNumber(2);

        given(productService.getProduct(any(), any(), any())).willReturn(null);

        mockMvc.perform(post("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accommodationRequest2)))
                .andExpect(result -> {
                    if (result.getResolvedException() instanceof ProductException) {
                        ProductException exception = (ProductException) result.getResolvedException();
                        assertEquals(ProductExceptionCode.CHECKIN_EQUALS_CHECKOUT.getMsg(), exception.getErrorCode().getMsg());
                    }
                });
    }

}