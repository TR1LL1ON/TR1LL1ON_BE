package com.ybe.tr1ll1on.domain.cart.controller;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "장바구니 API", description = "장바구니 관련 API 모음입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 전체 조회 API", description = "장바구니 전체 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = CartResponse.class)))
    @SecurityRequirement(name = "jwt")

    @GetMapping("")
    public List<CartResponse> getAllCartItems() {
        return cartService.getAllCarts();
    }


    @Operation(summary = "장바구니에 상품 추가 API", description = "장바구니에 상품 추가 API 입니다.")
    @ApiResponse(responseCode = "201", description = "추가 성공시",
            content = @Content(schema = @Schema(implementation = AddCartItemResponse.class)))
    @SecurityRequirement(name = "jwt")

    @PostMapping("/{productId}")
    public ResponseEntity<AddCartItemResponse> addCartItem(
            @PathVariable Long productId,
            @Valid @RequestBody @Schema(example = "{ \"checkIn\": \"2023-12-25\", \"checkOut\": \"2024-01-11\", \"personNumber\": 2, \"price\": 200000 }") AddCartItemRequest request
    ) {
        request.setProductId(productId);
        AddCartItemResponse response = cartService.addCartItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "장바구니에 상품 삭제 API", description = "장바구니에 상품 삭제 API 입니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공시")
    @SecurityRequirement(name = "jwt")
    @DeleteMapping("/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
    }

}
