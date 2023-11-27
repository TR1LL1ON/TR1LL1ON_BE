package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public GetCartResponse getAllCartItems() {
        return cartService.getAllCarts();
    }

    @PostMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddCartItemResponse addCartItem(@PathVariable Long productId, @RequestBody AddCartItemRequest request) {
        request.setProductId(productId);
        return cartService.addCartItem(request);
    }

    @DeleteMapping("/{cartId}")
    public RemoveCartItemResponse removeCartItem(@PathVariable Long cartId) {
        return cartService.removeCartItem(cartId);
    }

}
