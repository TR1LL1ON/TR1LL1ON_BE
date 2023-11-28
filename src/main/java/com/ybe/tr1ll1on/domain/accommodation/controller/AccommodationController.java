package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequestDTO;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponseDTO;
import com.ybe.tr1ll1on.domain.accommodation.error.InvalidDateException;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ybe.tr1ll1on.domain.accommodation.error.InvalidDateExceptionCode.CHECKIN_IS_AFTER_CHECKOUT;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/test")
    public List<AccommodationResponseDTO> getTest() {
        return accommodationService.getAll();
    }

    @PostMapping
    public ResponseEntity<List<AccommodationResponseDTO>> getAll(
            @RequestBody @Valid AccommodationRequestDTO accommodationRequestDTO,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region
    ) {
        if (!accommodationRequestDTO.getCheckOut().isAfter(accommodationRequestDTO.getCheckIn())) {
            throw new InvalidDateException(CHECKIN_IS_AFTER_CHECKOUT);
        }
        if (category != null) {
            accommodationRequestDTO.setCategory(category);
        }
        if (region != null) {
            accommodationRequestDTO.setAreaCode(region);
        }
        return ResponseEntity.ok(accommodationService.findAccommodation(accommodationRequestDTO));
    }
}
