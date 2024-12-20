package com.digital.wallet.service.mail;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final MailSenderService mailSenderService;
    private final MailTemplateService mailTemplateService;

    @Async
    public void sendRegistrationMailConfirmation(String email, String otp) throws MessagingException, TemplateException, IOException {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("validationCode", otp);
        String emailBody = mailTemplateService.getEmailContent("regEmailConfirmation.html", placeholders);
        mailSenderService.sendMail(email, "Confirm Mail", emailBody);
        log.info("Registration email verification mail send successfully");
    }

    @Async
    public void sendForgotPasswordMail(String email, String otp) throws MessagingException, TemplateException, IOException {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("validationCode", otp);
        String emailBody = mailTemplateService.getEmailContent("forgotPass.html", placeholders);
        mailSenderService.sendMail(email, "Password Reset Request", emailBody);
        log.info("Registration email verification mail send successfully");
    }
}
