package com.digital.wallet.service.security;

import com.digital.wallet.enums.EToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class JWTService {

    public boolean validateJwtToken(String authToken) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(EToken.ACCESS_SECRET.getValue().getBytes(StandardCharsets.UTF_8));
            Jwts.parser().verifyWith(secretKey).build().parse(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        }

        return false;
    }

    public String getSubjectDataFromJwtToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(EToken.ACCESS_SECRET.getValue().getBytes(StandardCharsets.UTF_8));

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String generateToken(String subject, EToken eToken) {
        Key secretKey = Keys.hmacShaKeyFor(EToken.ACCESS_SECRET.getValue().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().subject(subject).issuedAt(new Date()).
                expiration(createTokenExpiredTime(Integer.parseInt(eToken.getValue()))).signWith(secretKey).compact();
    }

    public Date createTokenExpiredTime(int hour) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.HOUR, hour);
        return instance.getTime();
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
