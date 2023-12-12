package com.ybe.tr1ll1on.domain.product.service;

import static com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode.EMPTY_PRODUCT;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationFacilityResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationImageResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductFacilityResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductImageResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductSummaryListResponse;
import com.ybe.tr1ll1on.domain.product.dto.response.ProductSummaryListResponse.ProductSummaryResponse;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.review.repository.ReviewRepository;
import com.ybe.tr1ll1on.domain.review.service.ReviewService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final AccommodationRepository accommodationRepository;
    private final ProductRepository productRepository;
    private final ProductInfoPerNightRepository productInfoPerNightRepository;
    private final ReviewRepository reviewRepository;

    private final ReviewService reviewService;

    /**
     * 숙박 상세 정보를 얻음.
     * @param accommodationId
     * @param request
     * @return
     */
    @Transactional
    public AccommodationDetailResponse getAccommodationDetail(
            Long accommodationId, AccommodationRequest request, Pageable pageable
    ) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        Accommodation accommodation = getAccommodation(accommodationId);

        List<Product> productList = productRepository
                .findByAccommodationAndMaximumNumberIsGreaterThanEqual(
                        accommodation, request.getPersonNumber());
        
        if (productList == null) {
            throw new ProductException(EMPTY_PRODUCT);
        }

        for (Product p : productList) {
            productResponseList.add(
                    getProductDetail(p, request)
            );
        }


        return AccommodationDetailResponse.builder()
                .accommodationId(accommodationId)
                .name(accommodation.getName())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .personNumber(request.getPersonNumber())
                .category(accommodation.getCategory().getCategoryCode())
                .address(accommodation.getAddress())
                .areaCode(accommodation.getAreaCode())
                .phone(accommodation.getPhone())
                .latitude(accommodation.getLatitude())
                .longitude(accommodation.getLongitude())
                .score(reviewRepository.getAvgReviewScore(accommodationId) == null? 0: reviewRepository.getAvgReviewScore(accommodationId))
                .rooms(productResponseList)
                .image(
                        accommodation.getAccommodationImageList().stream().map(
                                AccommodationImageResponse::of
                        ).collect(Collectors.toList())
                )
                .facility(
                        AccommodationFacilityResponse.of(
                                accommodation.getFacility()
                        )
                )
                .reviews(
                        reviewService.getProductReviews(accommodationId, pageable)
                )
                .build();
    }

    /**
     * 객실(상품) 정보를 얻음.
     * @param p
     * @param req
     * @return
     */
    private ProductResponse getProductDetail(Product p, AccommodationRequest req) {
        Double averPrice = 0.0;
        Integer totalPrice = 0;
        boolean isSold = true;
        Integer count = p.getCount(); //총 남은 객실
        LocalDate checkIn = req.getCheckIn(), checkOut = req.getCheckOut();

        /* TODO 체크아웃 날짜 -1 을 해야, 1박이 됨 */
        List<ProductInfoPerNight> productInfoPerNightList =
                productInfoPerNightRepository.findByDateBetweenAndProductId(
                        checkIn, checkOut.minusDays(1), p.getId()
                );

        for (ProductInfoPerNight pi : productInfoPerNightList) {
            if (pi.getCount() <= 0) {
                isSold = false;
                totalPrice = 0;
                count = 0;
                break;
            }
            totalPrice += pi.getPrice();
            count = Math.min(count, pi.getCount());
        }
        // TODO 평균 가격 계산 (체크아웃 - 체크인)
        averPrice = (double) (totalPrice / (Period.between(checkIn, checkOut).getDays()));

        return ProductResponse.builder()
                .roomId(p.getId())
                .roomName(p.getName())
                .checkIn(p.getCheckInTime())
                .checkOut(p.getCheckOutTime())
                .count(count)
                .isSold(isSold)
                .standardNumber(p.getStandardNumber())
                .maxNumber(p.getMaximumNumber())
                .averPrice(averPrice)
                .totalPrice(totalPrice)
                .image(
                        p.getProductImageList().stream().map(
                                ProductImageResponse::of
                        ).collect(Collectors.toList())
                )
                .facility(
                        ProductFacilityResponse.of(p.getProductFacility())
                )
                .build();
    }

    public ProductResponse getProductDetail(
            Long accommodationId, Long productId, AccommodationRequest request
    ) {
        return getProductDetail(getProduct(productId), request);
    }

    /**
     * 숙소 요약 정보 가져오는 메소드 (숙소 이름, 객실 정보, 이미지, 카테고리)
     * @param productIdListRequest
     * @return
     */
    public ProductSummaryListResponse getProductSummaryList(
            List<Long> productIdListRequest
    ) {
        List<ProductSummaryResponse> products = new ArrayList<>();
        for (Long id : productIdListRequest) {
            products.add(
                    ProductSummaryResponse.of(getProductSummary(id))
            );
        }
        return ProductSummaryListResponse.builder()
                .products(products)
                .build();
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
    }

    private Product getProductSummary(Long productId) {
        Product product =  productRepository.findByIdSummary(productId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
        log.info("ProductSummary ! ");
        return product;
    }

    private Accommodation getAccommodation(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
    }
}
