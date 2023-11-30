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

        @Schema(example = "플로팅웨일 설악도적폭포스테이")
        private String accommodationName;

        @Schema(example = "디럭스 룸")
        private String roomName;

        @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
        private String imageUrl;

        @Schema(example = "B02010100")
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
