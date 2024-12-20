package com.digital.wallet.service.user.impl;

import com.digital.wallet.dtos.user.AccessRequest;
import com.digital.wallet.entity.user.User;
import com.digital.wallet.exception.BadRequestException;
import com.digital.wallet.service.user.AccessProvider;
import com.digital.wallet.service.user.UserCredentialService;
import com.digital.wallet.validate.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class NativeAccessImpl implements AccessProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialService userCredentialService;

    @Override
    public User processRequest(AccessRequest accessRequest) {
        User user = userCredentialService.findByUserEmail(accessRequest.getUserEmail());
        UserValidator userValidator = new UserValidator();

        if (userValidator.isValidUser(user, accessRequest.getPassword(), passwordEncoder)) {
            return user;
        } else {
            throw new BadRequestException("Unable to validate user", 4100);
        }
    }
}
