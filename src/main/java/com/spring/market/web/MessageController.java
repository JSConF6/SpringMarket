package com.spring.market.web;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.chat.dto.ChatDto;
import com.spring.market.domain.chat.dto.ChatMessageDto;
import com.spring.market.domain.file.File;
import com.spring.market.domain.file.FileMapper;
import com.spring.market.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final FileMapper fileMapper;

    @MessageMapping("/chat/message")
    public void message(ChatDto chatDto) {
        chatService.createChatMessage(chatDto.getRoomId(), chatDto.getSenderId(), chatDto.getMessage());

        File senderImageFile = fileMapper.findUserImageByUserId(chatDto.getSenderId());

        String message = chatDto.getSenderId() + "/" + chatDto.getMessage();

        if (senderImageFile != null) {
            message += "/" + URLEncoder.encode(senderImageFile.getName(), StandardCharsets.UTF_8);
        }

        sendingOperations.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), message);
    }
}
