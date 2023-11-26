package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationRequestDTO;
import com.ybe.tr1ll1on.domain.accommodation.dto.AccommodationResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    List<AccommodationResponseDTO> findAvailableAccommodation(AccommodationRequestDTO request);
}
