package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.controller.CartController;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCartItems() {
        // Arrange
        GetCartResponse expectedResponse = new GetCartResponse();
        when(cartService.getAllCarts()).thenReturn(expectedResponse);

        // Act
        GetCartResponse actualResponse = cartController.getAllCartItems();

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(cartService, times(1)).getAllCarts();
    }

    @Test
    void addCartItem() {
        // Arrange
        Long productId = 1L;
        AddCartItemRequest request = new AddCartItemRequest();
        AddCartItemResponse expectedResponse = new AddCartItemResponse();
        when(cartService.addCartItem(request)).thenReturn(expectedResponse);

        // Act
        AddCartItemResponse actualResponse = cartController.addCartItem(productId, request);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(cartService, times(1)).addCartItem(request);
    }

    @Test
    void removeCartItem() {
        // Arrange
        Long cartId = 1L;
        RemoveCartItemResponse expectedResponse = new RemoveCartItemResponse();
        when(cartService.removeCartItem(cartId)).thenReturn(expectedResponse);

        // Act
        RemoveCartItemResponse actualResponse = cartController.removeCartItem(cartId);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(cartService, times(1)).removeCartItem(cartId);
    }
}
