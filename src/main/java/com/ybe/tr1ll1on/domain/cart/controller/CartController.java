package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")

public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public List<CartResponse> getAllCartItems() {
        return cartService.getAllCarts();
    }

    @PostMapping("/{productId}")
    public ResponseEntity<AddCartItemResponse> addCartItem(@PathVariable Long productId, @RequestBody AddCartItemRequest request) {
        request.setProductId(productId);
        AddCartItemResponse response = cartService.addCartItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{cartId}")
    public void removeCartItem(@PathVariable Long cartId) {
        cartService.removeCartItem(cartId);
    }

}
