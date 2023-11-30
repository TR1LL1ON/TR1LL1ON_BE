package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductImageResponse {

    @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
    private String imageUrl;

    public static ProductImageResponse of(ProductImage pi) {
        return ProductImageResponse.builder()
                .imageUrl(pi.getImageUrl())
                .build();
    }
}
