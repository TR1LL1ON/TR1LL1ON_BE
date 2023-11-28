package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    List<AccommodationResponse> findAvailableAccommodation(AccommodationRequest request);
}
