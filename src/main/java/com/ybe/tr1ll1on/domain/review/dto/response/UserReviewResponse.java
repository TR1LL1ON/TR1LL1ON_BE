package com.ybe.tr1ll1on.domain.review.dto.response;

import com.ybe.tr1ll1on.domain.review.model.Review;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviewResponse {
    @Schema(example = "1")
    private Long reviewId;
    @Schema(example = "2023-11-26")
    private LocalDate reviewDate;
    @Schema(example = "5.0")
    private double score;
    @Schema(example = "매우 만족합니다. 청결하네요!")
    private String content;

    @ArraySchema(schema = @Schema(implementation = AccommodationDetailsResponse.class))
    private AccommodationDetailsResponse accommodationDetails;
    @ArraySchema(schema = @Schema(implementation = ProductDetailsResponse.class))
    private ProductDetailsResponse productDetails;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AccommodationDetailsResponse {
        private Long accommodationId;
        private String accommodationName;

        public static AccommodationDetailsResponse fromEntity(Review review) {
            return AccommodationDetailsResponse.builder()
                    .accommodationId(review.getProduct().getAccommodation().getId())
                    .accommodationName(review.getProduct().getAccommodation().getName())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetailsResponse {
        private Long productId;
        private String productImage;
        private String productName;

        public static ProductDetailsResponse fromEntity(Review review) {
            return ProductDetailsResponse.builder()
                    .productId(review.getProduct().getId())
                    .productImage(review.getProduct().getProductImageList().get(0).getImageUrl())
                    .productName(review.getProduct().getName())
                    .build();
        }
    }

    public static UserReviewResponse fromEntity(Review review) {
        return UserReviewResponse.builder()
                .reviewId(review.getId())
                .accommodationDetails(AccommodationDetailsResponse.fromEntity(review))
                .productDetails(ProductDetailsResponse.fromEntity(review))
                .reviewDate(review.getReviewDate())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}
