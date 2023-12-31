package com.ybe.tr1ll1on.security.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SecurityExceptionCode implements ExceptionCode {

    // 인증 및 인가 관련 예외
    NOT_TOKEN(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "토큰이 제공되지 않았습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "유효하지 않은 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "403 Forbidden", "현재 요청한 작업을 수행할 권한이 없습니다."),

    // 사용자 관련 예외
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "사용자를 찾을 수 없습니다. 다시 시도하십시오."),

    // 충돌 관련 예외
    CONFLICT(HttpStatus.CONFLICT, "409 Conflict", "이미 존재하는 이메일입니다. 다른 이메일을 사용하거나, 로그인하십시오.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
