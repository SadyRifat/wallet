package com.digital.wallet.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class AccessRequest {
    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("password")
    private String password;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("grantType")
    @NotBlank(message = "Grant Type cannot be blank")
    private String grantType;
}
