package com.chawoomi.outbound.adapter.service;

import com.chawoomi.core.exception.user.UserCustomException;
import com.chawoomi.core.exception.user.UserErrorCode;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserCustomException(UserErrorCode.NO_USER_INFO));
    }

    public User findByUserName(String userName) {
        return userRepository.findByName(userName).orElseThrow(() -> new UserCustomException(UserErrorCode.NO_USER_INFO));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserCustomException(UserErrorCode.NO_USER_INFO));
    }
}
