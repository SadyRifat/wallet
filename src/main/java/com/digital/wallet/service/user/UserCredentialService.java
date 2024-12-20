package com.digital.wallet.service.user;

import com.digital.wallet.dtos.user.ForgotPasswordRequest;
import com.digital.wallet.dtos.user.PasswordSetupRequest;
import com.digital.wallet.dtos.user.RegistrationRequest;
import com.digital.wallet.entity.user.Role;
import com.digital.wallet.entity.user.User;
import com.digital.wallet.enums.ERole;
import com.digital.wallet.exception.BadRequestException;
import com.digital.wallet.exception.ResourceNotFoundException;
import com.digital.wallet.exception.SiteException;
import com.digital.wallet.model.BaseUser;
import com.digital.wallet.repository.user.UserCredentialRepository;
import com.digital.wallet.service.mail.MailService;
import com.digital.wallet.utls.ComplianceHelper;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Service
@AllArgsConstructor
public class UserCredentialService {
    private final MailService mailService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;

    public User addNewUserCredential(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(registrationRequest.getContactEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEnabled(false);
        user.setCreatedOn(ZonedDateTime.now());
        user.setUpdatedOn(ZonedDateTime.now());

        return assignAndSaveRole(user, ERole.ROLE_ADMIN);
    }

    public void userForgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, TemplateException, IOException {
        User user = findByUserEmail(forgotPasswordRequest.getEmail());
        if (user != null) {
            String otp = ComplianceHelper.generateOTP();
            mailService.sendForgotPasswordMail(forgotPasswordRequest.getEmail(), otp);
            user.setOtp(otp + "/" + DateTime.now().plusMinutes(30));
            userCredentialRepository.save(user);
        }
    }

    public void requestForEmailVerification(String userEmail) {
        User user = findByUserEmail(userEmail);
        if (user != null) {
            String otp = ComplianceHelper.generateOTP();
            try {
                mailService.sendRegistrationMailConfirmation(user.getEmail(), otp);
            } catch (MessagingException | TemplateException | IOException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
            user.setOtp(otp + "/" + DateTime.now().plusMinutes(30));
            userCredentialRepository.save(user);
        }
    }

    public String confirmIdentity(String userEmail, String otp) {
        User user = findByUserEmail(userEmail);
        if (validateOTP(otp, user.getOtp())) {
            user.setOtp(null);
            user.setEnabled(true);
        }
        userCredentialRepository.save(user);
        return null;
    }

    public void verityForgotPasswordUserIdentity(PasswordSetupRequest passwordSetupRequest, BaseUser baseUser) {
        String userEmail = passwordSetupRequest.getEmail();
        if (baseUser != null && !baseUser.getUsername().equals("ANONYMOUS")) {
            userEmail = baseUser.getUsername();
            User user = findByUserID(userEmail);
            if (passwordEncoder.matches(passwordSetupRequest.getOldPassword(), user.getPassword())) {
                user.setOtp("");
                user.setPassword(passwordEncoder.encode(passwordSetupRequest.getPassword()));
            }
            userCredentialRepository.save(user);
        } else {
            User user = userCredentialRepository.findByUserEmail(userEmail).
                    orElseThrow(() -> new ResourceNotFoundException("users", "userEmail", ""));
            if (validateOTP(passwordSetupRequest.getToken(), user.getOtp())) {
                user.setPassword(passwordEncoder.encode(passwordSetupRequest.getPassword()));
                user.setOtp("");
                userCredentialRepository.save(user);
            }
        }
    }

    public User assignAndSaveRole(User user, ERole erole) {
        Role roleUser = roleService.getRoleByName(erole);
        Set<Role> roles = user.getRoles();
        roles.add(roleUser);

        userCredentialRepository.save(user);
        log.info("New user create with user ID: {} and role: {}", user.getUserId(), user.getRoles());
        return user;
    }

    public boolean validateOTP(String userCode, String otp) {
        if (otp == null || otp.isBlank()) {
            throw new BadRequestException("Verification OTP code expired", 4112);
        }
        String code = ComplianceHelper.splitAndGet(otp, "/", 0);
        String date = ComplianceHelper.splitAndGet(otp, "/", 1);

        if (new DateTime().isBefore(new DateTime(date))) {
            if (code.equals(userCode)) {
                return true;
            } else {
                throw new BadRequestException("Invalid OTP code", 4111);
            }
        } else {
            throw new BadRequestException("Verification OTP code expired", 4112);
        }
    }

    public void resendOTP(String userEmail) throws MessagingException, TemplateException, IOException {
        User user = findByUserEmail(userEmail);
        if (!user.getOtp().isBlank() && !user.isEnabled()) {
            String otp = ComplianceHelper.generateOTP();
            user.setOtp(otp + "/" + DateTime.now().plusMinutes(30));
            userCredentialRepository.save(user);
            mailService.sendRegistrationMailConfirmation(userEmail, otp);
        } else {
            log.error("User: {}, not eligible for resent registration OTP", userEmail);
            throw new BadRequestException("User not eligible for resent registration OTP", 4007);
        }
    }

    public User findByUserEmail(String email) {
        return userCredentialRepository.findUserByEmail(email).orElse(null);
    }

    public User findByUserID(String id) {
        return userCredentialRepository.findById(id).orElse(null);
    }

    public User saveUserInfo(User user) {
        return userCredentialRepository.save(user);
    }
}
