package com.ybe.tr1ll1on.domain.product.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductFacilityResponse {
    private boolean canCook;

    private boolean hasAirConditioner;

    private boolean hasBath;

    private boolean hasCable;

    private boolean hasHairDryer;

    private boolean hasInternet;

    private boolean hasPc;

    private boolean hasRefrigerator;

    private boolean hasSofa;

    private boolean hasTv;

    private boolean hasTable;

    private boolean hasToiletries;

    public static ProductFacilityResponse of(ProductFacility productFacility) {
        return ProductFacilityResponse.builder()
                .canCook(productFacility.isCanCook())
                .hasPc(productFacility.isHasPc())
                .hasBath(productFacility.isHasBath())
                .hasAirConditioner(productFacility.isHasAirCondition())
                .hasCable(productFacility.isHasCable())
                .hasSofa(productFacility.isHasSofa())
                .hasTv(productFacility.isHasTv())
                .hasHairDryer(productFacility.isHasHairDryer())
                .hasInternet(productFacility.isHasInternet())
                .hasRefrigerator(productFacility.isHasRefrigerator())
                .hasTable(productFacility.isHasTable())
                .hasToiletries(productFacility.isHasToiletries())
                .build();
    }
}
