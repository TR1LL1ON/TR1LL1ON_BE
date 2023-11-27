package com.ybe.tr1ll1on.domain.review.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ReviewExceptionCode implements ExceptionCode {

    // 리뷰 관련 예외
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_CONFLICT(HttpStatus.CONFLICT, "409 Conflict", "이미 존재하는 리뷰 입니다."),

    // 주문 아이템 관련 예외
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "주문 상품을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
