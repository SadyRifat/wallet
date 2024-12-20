package com.digital.wallet.repository.user;

import com.digital.wallet.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}
