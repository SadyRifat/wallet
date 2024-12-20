package com.digital.wallet.service.user;

import com.digital.wallet.dtos.user.AccessRequest;
import com.digital.wallet.dtos.user.AccessToken;
import com.digital.wallet.entity.user.Role;
import com.digital.wallet.entity.user.User;
import com.digital.wallet.enums.ERole;
import com.digital.wallet.enums.EToken;
import com.digital.wallet.factory.AccessProviderFactory;
import com.digital.wallet.model.Consumer;
import com.digital.wallet.service.security.JWTService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class ApplicationTokenService {
    private final JWTService jwtService;
    private final AccessProviderFactory accessProviderFactory;

    public AccessToken requestForAccessToken(AccessRequest accessRequest) {
        AccessProvider accessProvider = accessProviderFactory.getAccessProvider(accessRequest.getGrantType());
        User user = accessProvider.processRequest(accessRequest);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(createAccessToken(user.getUserId(), user.getRoles()));
        accessToken.setExpireAt(ZonedDateTime.now().plusHours(Long.parseLong(EToken.ACCESS_DURATION.getValue())));
        accessToken.setRefreshToken(createRefreshToken(user.getUserId()));

        return accessToken;
    }

    public String createAccessToken(String userID, Set<Role> roles) {
        Gson gson = new Gson();
        Set<ERole> eRoles = new HashSet<>();
        for (Role role : roles) {
            eRoles.add(role.getName());
        }
        Consumer consumer = new Consumer(userID, eRoles);
        return jwtService.generateToken(gson.toJson(consumer), EToken.ACCESS_DURATION);
    }

    public String createRefreshToken(String userID) {
        return jwtService.generateToken(userID, EToken.REFRESH_DURATION);
    }
}
