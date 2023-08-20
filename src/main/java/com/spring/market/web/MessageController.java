package com.spring.market.web;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.chat.dto.ChatDto;
import com.spring.market.domain.chat.dto.ChatMessageDto;
import com.spring.market.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatDto chatDto) {
        chatService.createChatMessage(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());

        String message = chatDto.getSenderId() + "/" + chatDto.getMessage();
        sendingOperations.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), message);
    }
}
