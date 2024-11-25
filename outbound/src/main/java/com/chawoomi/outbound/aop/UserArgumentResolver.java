package com.chawoomi.outbound.aop;

import com.chawoomi.core.exception.jwt.SecurityCustomException;
import com.chawoomi.core.exception.jwt.SecurityErrorCode;
import com.chawoomi.domain.dto.UserInfoDTO;
import com.chawoomi.outbound.adapter.service.UserService;
import com.chawoomi.outbound.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(UserResolver.class);
        boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);
        return hasParameterAnnotation && isOrganizationParameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            String email = ((UserInfoDTO) principal).email();
            System.out.println("email ::: " + email);
            return userQueryService.findByEmail(email);
        } catch (ClassCastException e) {
            // 로그아웃된 토큰
            throw new SecurityCustomException(SecurityErrorCode.UNAUTHORIZED);
        }
    }
}
