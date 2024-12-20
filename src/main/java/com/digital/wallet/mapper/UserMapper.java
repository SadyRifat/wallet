package com.digital.wallet.mapper;

import com.digital.wallet.dtos.user.UserInfoResponse;
import com.digital.wallet.entity.user.UserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoResponse userInfoToUserInfoResponse(UserInfo userInfo);
}
