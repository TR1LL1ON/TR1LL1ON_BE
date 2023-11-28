package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.product.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /* select * from product where accommodation_id = ? and maximum_number >= ? */
    List<Product> findByAccommodationIdAndMaximumNumberIsGreaterThanEqual(
            Long accommodationId, Integer personNumber
    );
}
