package com.digital.wallet.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @JsonProperty("firstName")
    @NotBlank(message = "First Name is required.")
    @Size(max = 50, message = "Field length should not greater than 50")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "Last Name is required.")
    @Size(max = 50, message = "Field length should not greater than 50")
    private String lastName;

    @JsonProperty("contactPhone")
    @NotBlank(message = "Contact Phone is required.")
    @Size(max = 13, message = "Field length should not greater than 13")
    private String contactPhone;

    @JsonProperty("contactEmail")
    @NotBlank(message = "Email is required.")
    @Size(max = 100, message = "Field length should not greater than 100")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", message = "Email format is not valid")
    private String contactEmail;

    @JsonProperty("password")
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!])(?=\\S+$).{8,}$",
            message = "Choose a password of at least seven characters, one special character, one uppercase letter and one number")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String password;

    @JsonProperty("confirmPassword")
    @NotBlank(message = "Confirm Password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!])(?=\\S+$).{8,}$",
            message = "Choose a password of at least seven characters, one special character, one uppercase letter and one number")
    @Size(max = 20, message = "Field length should not greater than 20")
    private String confirmPassword;
}
