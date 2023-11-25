package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
