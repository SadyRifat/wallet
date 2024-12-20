package com.digital.wallet.validate;

import com.digital.wallet.entity.user.User;
import com.digital.wallet.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserValidator {
    public boolean isValidUser(User user, String requestPassword, PasswordEncoder passwordEncoder) {
        if (user == null) {
            throw new BadRequestException("User Not exists", 4101);
        } else if (!passwordEncoder.matches(requestPassword, user.getPassword())) {
            throw new BadRequestException("Password Mismatched", 4106);
        } else if (activityValidate(user)) {
            return true;
        } else {
            throw new BadRequestException("Unable to validate user", 4100);
        }
    }

    public boolean activityValidate(User user) {
        if (!user.isEnabled()) {
            throw new BadRequestException("User is not enabled", 4102);
        } else if (!user.isAccountNonExpired()) {
            throw new BadRequestException("User is Expired", 4103);
        } else if (!user.isAccountNonLocked()) {
            throw new BadRequestException("User is Locked", 4104);
        } else {
            return true;
        }
    }
}
