package com.ybe.tr1ll1on.domain.order.controller;

import com.ybe.tr1ll1on.domain.order.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.response.OrderResponse;
import com.ybe.tr1ll1on.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderResponse> order(
            @Valid @RequestBody final OrderRequest orderRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.order(orderRequest));
    }
}
