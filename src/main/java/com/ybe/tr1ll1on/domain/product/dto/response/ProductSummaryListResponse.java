package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.product.model.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductSummaryListResponse {

    private List<ProductSummaryResponse> products;

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductSummaryResponse {

        private String accommodationName;
        private String roomName;
        private String imageUrl;
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
