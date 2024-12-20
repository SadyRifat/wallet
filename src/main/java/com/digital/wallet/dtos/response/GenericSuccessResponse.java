package com.digital.wallet.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class GenericSuccessResponse {
    @JsonProperty("status")
    private int status = HttpStatus.OK.value();

    @JsonProperty("message")
    private String message;

    public GenericSuccessResponse(String message) {
        this.message = message;
    }
}
