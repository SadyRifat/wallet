package com.digital.wallet.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String contactEmail;
}
