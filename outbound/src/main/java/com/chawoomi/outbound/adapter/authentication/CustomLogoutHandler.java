package com.chawoomi.outbound.adapter.authentication;

import com.chawoomi.core.exception.jwt.SecurityCustomException;
import com.chawoomi.core.exception.jwt.SecurityErrorCode;
import com.chawoomi.core.redis.util.RedisUtil;
import com.chawoomi.outbound.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            log.info("[*] Logout Filter");

            String accessToken = jwtUtil.resolveAccessToken(request);

            redisUtil.saveAsValue(
                    accessToken,
                    "logout",
                    jwtUtil.getExpTime(accessToken),
                    TimeUnit.MILLISECONDS
            );

            redisUtil.delete(
                    jwtUtil.getUsername(accessToken) + "_refresh_token"
            );

        } catch (ExpiredJwtException e) {
            log.warn("[*] case : accessToken expired");
            throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED);
        }
    }
}
