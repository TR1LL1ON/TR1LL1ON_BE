package com.ybe.tr1ll1on.domain.product.controller;


import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductSummaryListResponse;
import com.ybe.tr1ll1on.domain.product.service.ProductService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "상품 API", description = "상품 관련 API 모음입니다.")

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")

public class ProductController {
    private final ProductService productService;


    @Operation(summary = "상품 요약 조회 API", description = "상품 요약 조회 API 입니다.")
    @ApiResponse(responseCode = "201", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = ProductSummaryListResponse.class)))
    @PostMapping("/summary")
    public ResponseEntity<ProductSummaryListResponse> getProductSummaryList(
            @RequestBody
            @Schema(example = "[\"3\"]")
            List<Long> productIdListRequest
    ) {
        return ResponseEntity.ok(
                productService.getProductSummaryList(productIdListRequest)
        );
    }

    @Operation(summary = "개별 상품 요약 조회 API", description = "개별 상품 요약 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = AccommodationDetailResponse.class)))
    @GetMapping("/{accommodation_id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(
        @PathVariable("accommodation_id") final Long accommodationId,
        @RequestParam(required = false) LocalDate checkIn,
        @RequestParam(required = false) LocalDate checkOut,
        @RequestParam(required = false) Integer personNumber
    ) {
        return ResponseEntity.ok(
                productService.getAccommodationDetail(accommodationId,
                        new AccommodationRequest(checkIn, checkOut, personNumber)
                )
        );
    }

    @Operation(summary = "상품 요약 조회 API", description = "상품 요약 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = ProductResponse.class)))
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
