package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductImageResponse {
    private String imageUrl;

    public static ProductImageResponse of(ProductImage pi) {
        return ProductImageResponse.builder()
                .imageUrl(pi.getImageUrl())
                .build();
    }
}
