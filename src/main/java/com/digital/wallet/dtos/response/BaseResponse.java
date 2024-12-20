package com.digital.wallet.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    @JsonProperty("data")
    Object object;

    @JsonProperty("code")
    int code;

    @JsonProperty("error")
    ErrorResponse errorResponse;
}
