package com.chawoomi.outbound.adapter;

import com.chawoomi.domain.dto.OidcDecodePayload;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginAdapter extends OidcUserService {

    private final UserRepository userRepository;
    private final KakaoIdTokenDecodeAdapter kakaoIdTokenDecodeAdapter;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            // Access Token 가져오기
            String tokenValue = userRequest.getAccessToken().getTokenValue();
            log.debug("Access Token: {}", tokenValue);

            // Access Token에서 유저 정보 디코딩 (Kakao 특화)
            OidcDecodePayload oidcDecodePayload = kakaoIdTokenDecodeAdapter.getPayloadFromAccessToken(tokenValue);
            log.debug("Decoded Payload: {}", oidcDecodePayload);

            // User 존재 여부 확인 및 조회
            userRepository.findBySubId(oidcDecodePayload.sub())
                    .orElseGet(() -> {
                        User newUser = User.createUser(oidcDecodePayload);
                        return userRepository.save(newUser);
                    });

            return new DefaultOidcUser(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    userRequest.getIdToken()
            );

        } catch (Exception e) {
            log.error("Error loading user from OAuth2 user request: {}", e.getMessage(), e);
            throw new OAuth2AuthenticationException("Error loading user from OAuth2 user request: " + e.getMessage());
        }
    }

}
