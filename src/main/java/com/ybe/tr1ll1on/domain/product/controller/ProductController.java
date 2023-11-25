package com.ybe.tr1ll1on.domain.product.controller;

import com.ybe.tr1ll1on.domain.product.dto.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/{accommodation_id}")
    public ResponseEntity<AccommodationDetailResponse> getProductList(
        @PathVariable("accommodation_id") final Long accommodationId,
        @RequestBody final AccommodationRequest accommodationRequest
    ) {
        log.info("HÎ11" + accommodationId+", "+ accommodationRequest.getPersonNumber());
        return ResponseEntity.ok(
                productService.getProduct(accommodationId, accommodationRequest)
        );
    }
}
