package com.ybe.tr1ll1on.domain.product.controller;


import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.service.ProductService;
import com.ybe.tr1ll1on.global.date.util.DateUtil;
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

    @GetMapping("/{accommodation_id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(
        @PathVariable("accommodation_id") final Long accommodationId,
        @RequestParam(required = false) LocalDate checkIn,
        @RequestParam(required = false) LocalDate checkOut,
        @RequestParam(required = false) Integer personNumber
    ) {
        AccommodationRequest request = new AccommodationRequest();
        if (checkIn != null) {
            request.setCheckIn(checkIn);
        }

        if (checkOut != null) {
            request.setCheckOut(checkOut);
        }

        if (personNumber != null) {
            request.setPersonNumber(personNumber);
        }
//        TODO checkIn 과 checkOut은 같은 날짜이면 안됨!!
        DateUtil.isValidCheckInBetweenCheckOut(
                request.getCheckIn(), request.getCheckOut()
        );

        return ResponseEntity.ok(
                productService.getAccommodationDetail(accommodationId, request)
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
        AccommodationRequest request = new AccommodationRequest();
        if (checkIn != null) {
            request.setCheckIn(checkIn);
        }

        if (checkOut != null) {
            request.setCheckOut(checkOut);
        }

        if (personNumber != null) {
            request.setPersonNumber(personNumber);
        }

        //TODO checkIn 과 checkOut은 같은 날짜이면 안됨!!
        DateUtil.isValidCheckInBetweenCheckOut(
                request.getCheckIn(), request.getCheckOut()
        );

        return ResponseEntity.ok(
                productService.getProduct(accommodationId, product_id, request)
        );
    }
}
