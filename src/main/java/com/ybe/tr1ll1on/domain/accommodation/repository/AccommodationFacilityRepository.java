package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationFacilityRepository extends JpaRepository<AccommodationFacility, Long> {
}
