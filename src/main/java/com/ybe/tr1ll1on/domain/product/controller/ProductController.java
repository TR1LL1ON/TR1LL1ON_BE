package com.ybe.tr1ll1on.domain.product.controller;


import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductSummaryListResponse;
import com.ybe.tr1ll1on.domain.product.service.ProductService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/summary")
    public ResponseEntity<ProductSummaryListResponse> getProductSummaryList(
            @RequestBody List<Long> productIdListRequest
    ) {
        return ResponseEntity.ok(
                productService.getProductSummaryList(productIdListRequest)
        );
    }

    @GetMapping("/{accommodation_id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(
        @PathVariable("accommodation_id") final Long accommodationId,
        @RequestParam(required = false) LocalDate checkIn,
        @RequestParam(required = false) LocalDate checkOut,
        @RequestParam(required = false) Integer personNumber
    ) {
        log.info("ProductController : checkIn = {}, checkOut = {}, personNumber = {}",
                checkIn, checkOut, personNumber);
        return ResponseEntity.ok(
                productService.getAccommodationDetail(accommodationId,
                        new AccommodationRequest(checkIn, checkOut, personNumber)
                )
        );
    }

    @GetMapping("/{accommodation_id}/{product_id}")
    public ResponseEntity<ProductResponse> getProductDetail(
            @PathVariable("accommodation_id") final Long accommodationId,
            @PathVariable("product_id") final Long product_id,
            @RequestParam(required = false) LocalDate checkIn,
            @RequestParam(required = false) LocalDate checkOut,
            @RequestParam(required = false) Integer personNumber
    ) {
        return ResponseEntity.ok(
                productService.getProductDetail(
                        accommodationId, product_id,
                        new AccommodationRequest(checkIn, checkOut, personNumber)
                )
        );
    }
}
