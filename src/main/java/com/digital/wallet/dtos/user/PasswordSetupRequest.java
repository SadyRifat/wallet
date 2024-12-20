package com.digital.wallet.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordSetupRequest {
    @JsonProperty("token")
    private String token;

    @JsonProperty("email")
    private String email;

    @JsonProperty("oldPassword")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!])(?=\\S+$).{7,}$",
            message = "Choose a password of at least seven characters, one special character, one uppercase letter and one number")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String oldPassword;

    @JsonProperty("password")
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!])(?=\\S+$).{7,}$",
            message = "Choose a password of at least seven characters, one special character, one uppercase letter and one number")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String password;

    @JsonProperty("confirmPassword")
    @NotBlank(message = "Confirm Password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!])(?=\\S+$).{7,}$",
            message = "Choose a password of at least seven characters, one special character, one uppercase letter and one number")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String confirmPassword;
}
