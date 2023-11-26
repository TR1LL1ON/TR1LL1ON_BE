package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
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
        request.setProductId(productId);  // 이 부분을 추가해 productId를 설정합니다.
        return cartService.addCartItem(request);
    }

    @DeleteMapping("/{cartItemId}")
    public RemoveCartItemResponse removeCartItem(@PathVariable Long cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }
}
