package com.chawoomi.inbounds.user.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.domain.dto.UserInfoDTO;
import com.chawoomi.outbound.adapter.service.UserService;
import com.chawoomi.outbound.aop.UserResolver;
import com.chawoomi.outbound.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 목록 조회", description = "유저 목록을 조회합니다.")
    @GetMapping()
    public ApplicationResponse<UserInfoDTO> getUser(
            @UserResolver User user
    ) {
        final User userEntity = userService.findByEmail(user.getEmail());
        UserInfoDTO infoDTO = new UserInfoDTO(userEntity.getName(), userEntity.getEmail(), userEntity.getPicture());
        return ApplicationResponse.ok(infoDTO);
    }
}
