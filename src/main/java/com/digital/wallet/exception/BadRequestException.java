package com.digital.wallet.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends IllegalArgumentException {
    int code;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
