package com.digital.wallet.factory;

import com.digital.wallet.enums.EAuthProvider;
import com.digital.wallet.exception.BadRequestException;
import com.digital.wallet.service.user.AccessProvider;
import com.digital.wallet.service.user.impl.NativeAccessImpl;
import com.digital.wallet.service.user.impl.RefreshAccessImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AccessProviderFactory {
    private final NativeAccessImpl nativeAccess;
    private final RefreshAccessImpl refreshAccess;

    public AccessProvider getAccessProvider(String grandType) {
        if (grandType.equalsIgnoreCase(EAuthProvider.password.name())) {
            return nativeAccess;
        } else if (grandType.equalsIgnoreCase(EAuthProvider.refresh.name())) {
            return refreshAccess;
        } else {
            throw new BadRequestException("Unknown Grant Type", 4113);
        }
    }
}
