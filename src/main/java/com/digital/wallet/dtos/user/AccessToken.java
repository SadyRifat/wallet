package com.digital.wallet.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessToken {
    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("expireAt")
    private ZonedDateTime expireAt;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
