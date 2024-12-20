package com.digital.wallet.controller.user;

import com.digital.wallet.dtos.response.BaseResponse;
import com.digital.wallet.dtos.user.*;
import com.digital.wallet.exception.BadRequestException;
import com.digital.wallet.model.BaseUser;
import com.digital.wallet.service.security.CurrentUser;
import com.digital.wallet.service.user.ApplicationTokenService;
import com.digital.wallet.service.user.UserCredentialService;
import com.digital.wallet.service.user.UserService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserCredentialService userCredentialService;
    private final ApplicationTokenService applicationTokenService;

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            throw new BadRequestException("User Password Mismatched", 4006);
        }
        UserInfoResponse userInfoResponse = userService.addNewUser(registrationRequest);
        if (userInfoResponse != null) {
            userCredentialService.requestForEmailVerification(registrationRequest.getContactEmail());
        }
        log.info("New Registration Success");
        return new ResponseEntity<>(new BaseResponse(userInfoResponse, HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @GetMapping("/confirm-identity/{userEmail}/{otp}")
    public ResponseEntity<?> confirmIdentity(@PathVariable(value = "userEmail") String userEmail, @PathVariable(value = "otp") String validationOtp) {
        String token = userCredentialService.confirmIdentity(userEmail, validationOtp);
        return new ResponseEntity<>(new BaseResponse(token, HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AccessRequest accessRequest) {
        AccessToken accessToken = applicationTokenService.requestForAccessToken(accessRequest);
        return new ResponseEntity<>(new BaseResponse(accessToken, HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, TemplateException, IOException {
        userCredentialService.userForgotPassword(forgotPasswordRequest);
        return new ResponseEntity<>(new BaseResponse("Success", HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @GetMapping("/resend-opt/{userEmail}")
    public ResponseEntity<?> optResend(@PathVariable(value = "userEmail") String userEmail) throws MessagingException, TemplateException, IOException {
        userCredentialService.resendOTP(userEmail);
        return new ResponseEntity<>(new BaseResponse("Success", HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @PostMapping("/password-setup")
    public ResponseEntity<?> passwordSetup(@Valid @RequestBody PasswordSetupRequest passwordSetupRequest, @CurrentUser BaseUser baseUser) {
        if (!passwordSetupRequest.getPassword().equals(passwordSetupRequest.getConfirmPassword())) {
            throw new BadRequestException("User Password Mismatched", 4006);
        }
        userCredentialService.verityForgotPasswordUserIdentity(passwordSetupRequest, baseUser);
        return new ResponseEntity<>(new BaseResponse("Success", HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserProfile(@CurrentUser BaseUser baseUser) {
        UserInfoResponse userInfoResponse = userService.makeUserProfileResponse(baseUser);
        return new ResponseEntity<>(new BaseResponse(userInfoResponse, HttpServletResponse.SC_OK, null), HttpStatus.OK);
    }
}
