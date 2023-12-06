package com.ybe.tr1ll1on.domain.accommodation.service;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationMapper;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper mapper;

    @Transactional
    public List<AccommodationResponse> findAccommodation(AccommodationRequest accommodationRequest) {

        return mapper.findAvailableAccommodation(accommodationRequest)
                .stream()
                .map(it -> AccommodationResponse.builder()
                        .accommodationId(it.getAccommodationId())
                        .imageUrl(it.getImageUrl() == null ? AccommodationImage.BASIC_ACCOMMODATION_IMG : it.getImageUrl())
                        .name(it.getName())
                        .price(it.getPrice())
                        .address(it.getAddress())
                        .areaCode(it.getAreaCode())
                        .latitude(it.getLatitude())
                        .longitude(it.getLongitude())
                        .score(randomScore())
                        .build())
                .collect(Collectors.toList());
    }

    private Double randomScore() {
        double score = Math.random() * 5.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(score));
    }
}
