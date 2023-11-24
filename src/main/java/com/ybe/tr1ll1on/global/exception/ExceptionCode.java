package com.ybe.tr1ll1on.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getStatus();
    String getCode();
    String getMsg();
}
