package com.digital.wallet.service.user;

import com.digital.wallet.entity.user.Role;
import com.digital.wallet.enums.ERole;
import com.digital.wallet.repository.user.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByName(ERole role) {
        return roleRepository.getRoleByName(role);
    }
}
