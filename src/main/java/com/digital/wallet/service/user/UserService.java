package com.digital.wallet.service.user;

import com.digital.wallet.dtos.user.RegistrationRequest;
import com.digital.wallet.dtos.user.UserInfoResponse;
import com.digital.wallet.entity.user.User;
import com.digital.wallet.entity.user.UserInfo;
import com.digital.wallet.exception.BadRequestException;
import com.digital.wallet.exception.ResourceNotFoundException;
import com.digital.wallet.mapper.UserMapper;
import com.digital.wallet.model.BaseUser;
import com.digital.wallet.repository.user.UserInfoRepository;
import com.digital.wallet.service.mail.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserInfoRepository userInfoRepository;
    private final UserCredentialService userCredentialService;
    private final MailService mailService;

    public UserInfoResponse addNewUser(RegistrationRequest registrationRequest) {
        if (userCredentialService.findByUserEmail(registrationRequest.getContactEmail()) == null) {
            return saveNewUser(registrationRequest);
        } else {
            log.error("User Already exists: {}", registrationRequest.getContactEmail());
            throw new BadRequestException("User Already exists.", 4005);
        }
    }

    public UserInfoResponse saveNewUser(RegistrationRequest registrationRequest) {
        User user = userCredentialService.addNewUserCredential(registrationRequest);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getUserId());
        userInfo.setFirstName(registrationRequest.getFirstName());
        userInfo.setLastName(registrationRequest.getLastName());
        userInfo.setContactEmail(registrationRequest.getContactEmail());
        userInfo.setContactPhone(registrationRequest.getContactPhone());
        userInfo.setCreatedOn(ZonedDateTime.now());
        userInfo.setUpdatedOn(ZonedDateTime.now());
        userInfoRepository.save(userInfo);
        log.info("New registration UserProfile saved with userInfo ID: {}", userInfo.getId());

        return userMapper.userInfoToUserInfoResponse(userInfo);
    }

    public UserInfoResponse makeUserProfileResponse(BaseUser baseUser) {
        UserInfo userProfileByID = findInstituteByID(baseUser.getUsername());
        return userMapper.userInfoToUserInfoResponse(userProfileByID);
    }

    public UserInfo findInstituteByID(String userID) {
        return userInfoRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("UserProfile", "userID", userID));
    }

    public UserInfo getMemberByID(String userID) {
        return userInfoRepository.findById(userID).orElse(null);
    }
}


