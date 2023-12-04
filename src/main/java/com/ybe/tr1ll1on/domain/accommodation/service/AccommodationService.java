package com.ybe.tr1ll1on.domain.accommodation.service;

import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import java.util.List;

public interface AccommodationService {
    List<AccommodationResponse> findAccommodation(AccommodationRequest accommodationRequest);
}
