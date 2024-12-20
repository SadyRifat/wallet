package com.digital.wallet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SiteException extends Exception {
    protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public SiteException(String message) {
        super(message);
    }

    public SiteException(String message, Throwable cause) {
        super(message, cause);
    }
}
