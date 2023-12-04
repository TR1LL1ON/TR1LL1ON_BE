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
public class ProductReviewResponse {
    @Schema(example = "1")
    private Long reviewId;
    @Schema(example = "2023-11-26")
    private LocalDate reviewDate;
    @Schema(example = "5.0")
    private double score;
    @Schema(example = "매우 만족합니다. 청결하네요!")
    private String content;

    @ArraySchema(schema = @Schema(implementation = ProductReviewResponse.UserDetailsResponse.class))
    private UserDetailsResponse userDetails;
    @ArraySchema(schema = @Schema(implementation = ProductReviewResponse.ProductDetailsResponse.class))
    private ProductDetailsResponse productDetails;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDetailsResponse {
        private Long userId;
        private String userName;

        public static UserDetailsResponse fromEntity(Review review) {

            return UserDetailsResponse.builder()
                    .userId(review.getUser().getId())
                    .userName(review.getUser().getName())
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

    public static ProductReviewResponse fromEntity(Review review) {

        return ProductReviewResponse.builder()
                .reviewId(review.getId())
                .userDetails(UserDetailsResponse.fromEntity(review))
                .productDetails(ProductDetailsResponse.fromEntity(review))
                .score(review.getScore())
                .reviewDate(review.getReviewDate())
                .content(review.getContent())
                .build();
    }
}
