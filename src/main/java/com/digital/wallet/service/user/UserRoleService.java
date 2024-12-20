package com.digital.wallet.service.user;

import com.digital.wallet.repository.user.UserRoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public void saveUserRole() {

    }
}
