package com.chawoomi.core.exception.user;

import com.chawoomi.core.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class UserCustomException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public UserCustomException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public UserCustomException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}
