package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.product.model.Product;
import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductSummaryListResponse {

    @ArraySchema(schema = @Schema(implementation = ProductSummaryResponse.class))
    private List<ProductSummaryResponse> products;

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductSummaryResponse {

        @Schema(description = "숙소 이름", defaultValue = "숙소 이름")
        private String accommodationName;
        @Schema(description = "객실 이름", defaultValue = "객실 이름")
        private String roomName;
        @Schema(description = "객실 이미지 주소", defaultValue = "객실 이미지 주소")
        private String imageUrl;
        @Schema(description = "숙소 카테고리", defaultValue = "B02010100")
        private String category;

        public static ProductSummaryResponse of(Product product) {
            return ProductSummaryResponse.builder()
                    .accommodationName(product.getAccommodation().getName())
                    .roomName(product.getName())
                    .imageUrl(product.getAccommodation().getImages().get(0).getImageUrl())
                    .category(product.getAccommodation().getCategory().getCategoryCode())
                    .build();
        }
    }
}
