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

    @Transactional
    public AccommodationDetailResponse getProduct (
            Long accommodationId, AccommodationRequest request
    ) {
        List<ProductResponse> productResponseList = new ArrayList<>();

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
        List<Product> productList = productRepository
                .findByAccommodationIdAndStandardNumberLessThanEqual(
                        accommodationId, request.getPersonNumber());
        
        if (productList == null) {
            throw new ProductException(EMPTY_PRODUCT);
        }

        for (Product p : productList) {
            productResponseList.add(
                    getProduct(
                            p, request.getCheckIn(), request.getCheckOut().minusDays(1)
                    )
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

    private ProductResponse getProduct(Product p, LocalDate checkIn, LocalDate checkOut) {
        //checkIn, checkOut dateUtil 필요.
        //checkIn, checkOut 에서 날짜 범위가 11.24 ~ 12.10 넘어가면 조정 필ㅊ
        Double averPrice = 0.0;
        Integer totalPrice = 0;
        boolean isSold = true;
        Integer count = p.getCount();
        List<ProductInfoPerNight> productInfoPerNightList =
                productInfoPerNightRepository.findByDateBetweenAndProductId(
                        checkIn, checkOut, p.getId()
                );
        for (ProductInfoPerNight pi : productInfoPerNightList) {
            if (pi.getCount() <= 0) {
                isSold = false;
                totalPrice = 0;
                count = -1;
                break;
            }
            totalPrice += pi.getPrice();
            count = Math.min(count, pi.getCount());
        }
        //평균 가격 계산
        averPrice = (double) (totalPrice / (Period.between(checkIn, checkOut).getDays() + 1));

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
                .build()
                ;
    }
}
