package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFacilityRepository extends JpaRepository<ProductFacility, Long> {

    ProductFacility findByProductId(Long id);
}