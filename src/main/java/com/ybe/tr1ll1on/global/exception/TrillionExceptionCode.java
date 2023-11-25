package com.ybe.tr1ll1on.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TrillionExceptionCode implements ExceptionCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR" ,"서버에 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청 입니다."),

    // 인증 및 인가 관련 예외
    NOT_TOKEN(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "토큰이 제공되지 않았습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "유효하지 않은 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "403 Forbidden", "현재 요청한 작업을 수행할 권한이 없습니다."),

    // 사용자 관련 예외
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "사용자를 찾을 수 없습니다. 다시 시도하십시오."),

    // 충돌 관련 예외
    CONFLICT(HttpStatus.CONFLICT, "409 Conflict", "이미 존재하는 이메일입니다. 다른 이메일을 사용하거나, 로그인하십시오."),

    // 리뷰 관련 예외
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_CONFLICT(HttpStatus.CONFLICT, "409 Conflict", "이미 존재하는 리뷰 입니다."),

    // 주문 아이템 관련 예외
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_ITEM_NOT_FOUND", "주문 상품을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String msg;
}
