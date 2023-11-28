package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponseDTO;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<List<AccommodationResponseDTO>> getAll(
            @RequestBody @Valid AccommodationRequest accommodationRequestDTO,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region
    ) {
        if (category != null) {
            accommodationRequestDTO.setCategory(category);
        }
        if (region != null) {
            accommodationRequestDTO.setAreaCode(region);
        }
        return ResponseEntity.ok(accommodationService.findAccommodation(accommodationRequestDTO));
    }
}
