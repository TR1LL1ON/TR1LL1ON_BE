package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "숙소 API", description = "숙소 관련 API 모음입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class AccommodationController {
    private final AccommodationServiceImpl accommodationService;

    @GetMapping
    @Operation(summary = "숙소 전체 조회 API", description = "숙소 전체 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시",
            content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    public ResponseEntity<List<AccommodationResponse>> get(
            @RequestParam(required = false) LocalDate checkIn,
            @RequestParam(required = false) LocalDate checkOut,
            @RequestParam(required = false) Integer personNumber,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer maxId
    ) {
        return ResponseEntity.ok(accommodationService.findAccommodation(
                new AccommodationRequest(checkIn, checkOut, personNumber, category, region, pageSize, maxId)
        ));
    }




}


