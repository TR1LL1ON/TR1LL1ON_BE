package com.ybe.tr1ll1on.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AreaCode {

    서울("1"),
    인천("2"),
    대전("3"),
    대구("4"),
    광주("5"),
    부산("6"),
    울산("7"),
    세종("8"),
    경기도("31"),
    강원도("32"),
    충청북도("33"),
    충청남도("34"),
    경상북도("35"),
    경상남도("36"),
    전라북도("37"),
    전라남도("38"),
    제주도("39")
    ;


    private String code;
}
