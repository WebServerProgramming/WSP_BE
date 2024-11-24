package com.chawoomi.inbounds.chat.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chat")
@Tag(name = "Chat API", description = "챗봇 관련 API")
public class ChatController {

    private final ChatService chatService;

    @GetMapping()
    @Operation(
            summary = "ChatGPT OpenAI 응답 가져오기",
            description = "주어진 키워드와 타입에 따라 ChatGPT OpenAI API로부터 응답을 가져옵니다.",
            tags = {"Chat API"}
    )
    public ApplicationResponse<String> getChatGptOpenApi(
            @RequestParam String keyword
    ) throws IOException {

        final String chatGPTResponse = chatService.getChatGPTResponse(keyword);
        return ApplicationResponse.ok(chatGPTResponse);
    }
}
