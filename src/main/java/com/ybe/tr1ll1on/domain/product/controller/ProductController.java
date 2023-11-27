package com.ybe.tr1ll1on.domain.product.controller;

import static com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode.CHECKIN_EQUALS_CHECKOUT;

import com.ybe.tr1ll1on.domain.product.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.service.ProductService;
import com.ybe.tr1ll1on.global.date.utill.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/{accommodation_id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(
        @PathVariable("accommodation_id") final Long accommodationId,
        @RequestBody final AccommodationRequest accommodationRequest
    ) {
//        TODO checkIn 과 checkOut은 같은 날짜이면 안됨!!
        DateUtil.isValidCheckInBetweenCheckOut(
                accommodationRequest.getCheckIn(), accommodationRequest.getCheckOut()
        );
        return ResponseEntity.ok(
                productService.getAccommodationDetail(accommodationId, accommodationRequest)
        );
    }

    @PostMapping("/{accommodation_id}/{product_id}")
    public ResponseEntity<ProductResponse> getProductDetail(
            @PathVariable("accommodation_id") final Long accommodationId,
            @PathVariable("product_id") final Long product_id,
            @RequestBody final AccommodationRequest accommodationRequest
    ) {
        //TODO checkIn 과 checkOut은 같은 날짜이면 안됨!!
        DateUtil.isValidCheckInBetweenCheckOut(
                accommodationRequest.getCheckIn(), accommodationRequest.getCheckOut()
        );
        return ResponseEntity.ok(
                productService.getProduct(accommodationId, product_id, accommodationRequest)
        );
    }
}
