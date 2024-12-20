package com.digital.wallet.service.user;

import com.digital.wallet.dtos.user.AccessRequest;
import com.digital.wallet.entity.user.User;

public interface AccessProvider {
    User processRequest(AccessRequest accessRequest);
}
