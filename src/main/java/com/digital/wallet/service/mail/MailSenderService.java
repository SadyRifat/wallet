package com.digital.wallet.service.mail;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender emailSender;

    protected void sendMail(String to, String subject, String template) throws MessagingException {
        sendMail(to, subject, template, null);
    }

    protected void sendMail(String to, String subject, String template, List<byte[]> files) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(template, true);

        if (files != null) {
            try {
                int fileCount = 1;
                for (byte[] file : files) {
                    ByteArrayDataSource attachmentDataSource = new ByteArrayDataSource(file, getMimeType(file));
                    String fileName = StringUtils.hasText(attachmentDataSource.getName()) ? ("File-" + fileCount++) : attachmentDataSource.getName();
                    helper.addAttachment(fileName, attachmentDataSource);
                }
            } catch (IOException | MessagingException e) {
                e.printStackTrace();
            }
        }
        emailSender.send(message);
    }

    private String getMimeType(byte[] data) throws IOException {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        return URLConnection.guessContentTypeFromStream(is);
    }
}
