package com.ybe.tr1ll1on.domain.product.service;

import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductSummaryListResponse;
import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import java.util.List;

public interface ProductService {
    AccommodationDetailResponse getAccommodationDetail(Long accommodationId, AccommodationRequest request);
    ProductResponse getProductDetail(Long accommodationId, Long productId, AccommodationRequest request);
    ProductSummaryListResponse getProductSummaryList(List<Long> productIdListRequest);

}
