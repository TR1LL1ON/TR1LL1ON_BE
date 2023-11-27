package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
