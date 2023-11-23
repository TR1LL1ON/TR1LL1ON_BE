package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.dto.CartItemResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public List<CartItemResponse> getAllCartList(){
        return cartService.getAllCartItems();
    }

    @PostMapping("/{cartItemId}")
    public void postProduct(@PathVariable long cartItemId){

    }

    @DeleteMapping("/{cartItemId}")
    public void deleteProduct(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
    }
}
