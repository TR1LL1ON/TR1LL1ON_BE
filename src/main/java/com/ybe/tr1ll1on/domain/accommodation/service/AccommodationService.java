package com.ybe.tr1ll1on.domain.accommodation.service;

import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationRequestDTO;
import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationResponseDTO;
import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationMapper;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper mapper;

    @Transactional
    public List<AccommodationResponseDTO> getAll() {

        List<AccommodationResponseDTO> accommodationResponseDTOList = new ArrayList<>();

        List<Accommodation> accommodationList = accommodationRepository.findAll();
        for (Accommodation accommodation : accommodationList) {
            accommodationResponseDTOList.add(
                    AccommodationResponseDTO.builder()
                            .accommodationId(accommodation.getId())
                            .name(accommodation.getName())
                    .build());
        }
        return accommodationResponseDTOList;
    }

    @Transactional
    public List<AccommodationResponseDTO> findAccommodation(AccommodationRequestDTO accommodationRequestDTO){
        return mapper.findAvailableAccommodation(accommodationRequestDTO)
                .stream()
                .map(it -> AccommodationResponseDTO.builder()
                        .accommodationId(it.getAccommodationId())
                        .imageUrl(it.getImageUrl()==null ? AccommodationImage.BASIC_ACCOMMODATION_IMG : it.getImageUrl())
                        .name(it.getName())
                        .price(it.getPrice())
                        .address(it.getAddress())
                        .areaCode(it.getAreaCode())
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
