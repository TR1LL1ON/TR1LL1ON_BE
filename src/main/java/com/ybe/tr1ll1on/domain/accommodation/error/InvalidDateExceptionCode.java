package com.ybe.tr1ll1on.domain.accommodation.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum InvalidDateExceptionCode implements ExceptionCode {

    CHECKIN_IS_AFTER_CHECKOUT(HttpStatus.BAD_REQUEST, "CHECKIN_IS_AFTER_CHECKOUT", "체크인 날짜는 체크아웃 날짜 이후일 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
