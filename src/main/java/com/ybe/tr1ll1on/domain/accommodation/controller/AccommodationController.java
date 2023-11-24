package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationResponseDTO;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/")
    public List<AccommodationResponseDTO> getAll() {
        return accommodationService.getAll();
    }
}
