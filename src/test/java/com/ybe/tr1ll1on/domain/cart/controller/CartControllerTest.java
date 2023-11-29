package com.ybe.tr1ll1on.domain.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCartItemTest() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        AddCartItemRequest request = new AddCartItemRequest();
        request.setCheckIn(LocalDate.parse("2023-11-21"));
        request.setCheckOut(LocalDate.parse("2023-11-22"));
        request.setPersonNumber(2);
        request.setPrice(20000);

        AddCartItemResponse mockResponse = new AddCartItemResponse();
        mockResponse.setCartItemId(1L);
        mockResponse.setProductId(100L);
        mockResponse.setPersonNumber(2);

        when(cartService.addCartItem(any())).thenReturn(mockResponse);

        this.mockMvc.perform(post("/carts/{productId}", 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.personNumber").exists())
                .andDo(print());

    }


    @Test
    public void getAllCartItemsTest() throws Exception {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartItemId(1L);
        cartResponse.setAccommodationName("신라호텔");
        cartResponse.setAccommodationAddress("제주특별자치도 서귀포시 중문관광로72번길 75");
        cartResponse.setAccommodationCategory("B02010100");
        cartResponse.setProductName("더블 스탠다드룸");
        cartResponse.setCheckIn(LocalDate.parse("2023-11-21"));
        cartResponse.setCheckOut(LocalDate.parse("2023-11-22"));
        cartResponse.setPersonNumber(2);
        cartResponse.setPrice(20000);

        when(cartService.getAllCarts()).thenReturn(List.of(cartResponse));

        this.mockMvc.perform(get("/carts"))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCartItemTest() throws Exception {

        Long cartItemIdToRemove = 1L;

        mockMvc.perform(delete("/carts/{cartItemId}", cartItemIdToRemove))
                .andExpect(status().isOk());
    }
}
