package com.ybe.tr1ll1on.domain.accommodation.service;

import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationResponseDTO;
import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

    @Transactional
    public List<AccommodationResponseDTO> getAll() {

        List<AccommodationResponseDTO> accommodationResponseDTOList = new ArrayList<>();

        List<Accommodation> accommodationList = accommodationRepository.findAll();
        for (Accommodation accommodation : accommodationList) {
            accommodationResponseDTOList.add(
                    new AccommodationResponseDTO(accommodation.getId(), accommodation.getName()));
        }
        return accommodationResponseDTOList;
    }
}
