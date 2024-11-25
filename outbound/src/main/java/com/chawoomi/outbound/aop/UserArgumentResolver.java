package com.chawoomi.outbound.aop;

import com.chawoomi.core.exception.jwt.SecurityCustomException;
import com.chawoomi.core.exception.jwt.SecurityErrorCode;
import com.chawoomi.outbound.adapter.service.UserService;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userQueryService;
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(UserResolver.class);
        boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);
        return hasParameterAnnotation && isOrganizationParameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // 1. Authorization 헤더에서 토큰 추출
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new SecurityCustomException(SecurityErrorCode.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7); // "Bearer " 제거

        try {
            // 2. 토큰에서 이메일 추출
            String email = jwtUtil.getEmailFromToken(token); // JWT 파싱 메서드 호출
            System.out.println("email ::: " + email);

            // 3. 이메일로 사용자 정보 조회
            return userQueryService.findByEmail(email);
        } catch (Exception e) {
            throw new SecurityCustomException(SecurityErrorCode.UNAUTHORIZED);
        }
    }

}
