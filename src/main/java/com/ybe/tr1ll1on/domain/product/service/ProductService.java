package com.ybe.tr1ll1on.domain.product.service;

import static com.ybe.tr1ll1on.domain.product.error.ProductExceptionCode.EMPTY_PRODUCT;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
import com.ybe.tr1ll1on.domain.product.dto.AccommodationDetailResponse;
import com.ybe.tr1ll1on.domain.product.dto.AccommodationFacilityResponse;
import com.ybe.tr1ll1on.domain.product.dto.AccommodationImageResponse;
import com.ybe.tr1ll1on.domain.product.dto.AccommodationRequest;
import com.ybe.tr1ll1on.domain.product.dto.ProductFacilityResponse;
import com.ybe.tr1ll1on.domain.product.dto.ProductImageResponse;
import com.ybe.tr1ll1on.domain.product.dto.ProductResponse;
import com.ybe.tr1ll1on.domain.product.error.ProductException;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final AccommodationRepository accommodationRepository;
    private final ProductRepository productRepository;
    private final ProductInfoPerNightRepository productInfoPerNightRepository;

    /**
     * 숙박 상세 정보를 얻음.
     * @param accommodationId
     * @param request
     * @return
     */
    @Transactional
    public AccommodationDetailResponse getAccommodationDetail(
            Long accommodationId, AccommodationRequest request
    ) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
        List<Product> productList = productRepository
                .findByAccommodationIdAndStandardNumberLessThanEqualAndMaximumNumberGreaterThanEqual(
                        accommodationId, request.getPersonNumber(), request.getPersonNumber());
        
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
                .score(5.0)
                .rooms(productResponseList)
                .image(
                        accommodation.getImages().stream().map(
                                it -> AccommodationImageResponse.of(it)
                        ).collect(Collectors.toList())
                )
                .facility(
                        AccommodationFacilityResponse.of(
                            accommodation.getFacility()
                        )
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
                                it -> ProductImageResponse.of(it)
                        ).collect(Collectors.toList())
                )
                .facility(
                        ProductFacilityResponse.of(p.getProductFacility())
                )
                .build();
    }

    public ProductResponse getProduct(
            Long accommodationId, Long productId, AccommodationRequest request
    ) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));

        return getProductDetail(product, request);
    }
}
