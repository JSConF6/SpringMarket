package com.spring.market.domain.chat.dto;

import com.spring.market.domain.chat.ChatMessage;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageDto {
    private int id;
    private String messageDate;
    private List<ChatMessage> chatMessage;
}
