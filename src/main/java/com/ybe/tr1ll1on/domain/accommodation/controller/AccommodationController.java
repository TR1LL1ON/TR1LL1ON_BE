package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationService;
import com.ybe.tr1ll1on.global.date.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/test")
    public List<AccommodationResponse> getTest() {
        return accommodationService.getAll();
    }

    @GetMapping
    public ResponseEntity<List<AccommodationResponse>> get(
            @RequestParam(required = false) LocalDate checkIn,
            @RequestParam(required = false) LocalDate checkOut,
            @RequestParam(required = false) Integer personNumber,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region
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

        if (category != null) {
            request.setCategory(category);
        }

        if (region != null) {
            request.setAreaCode(region);
        }

        DateUtil.isValidCheckInBetweenCheckOut(request.getCheckIn(), request.getCheckOut());

        return ResponseEntity.ok(accommodationService.findAccommodation(request));
    }


}
