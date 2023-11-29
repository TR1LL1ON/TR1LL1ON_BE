package com.ybe.tr1ll1on.domain.order.controller;

import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.order.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.response.OrderResponse;
import com.ybe.tr1ll1on.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 API", description = "주문 관련 API 모음입니다.")

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문하기 API", description = "주문하기 API 입니다.")
    @ApiResponse(responseCode = "201", description = "주문 성공시",
            content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    @SecurityRequirement(name = "jwt")
    @PostMapping("")
    public ResponseEntity<OrderResponse> order(
            @Valid @RequestBody final OrderRequest orderRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.order(orderRequest));
    }
}
