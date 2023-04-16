package com.spring.market.domain.chat.dto;

import lombok.Data;

@Data
public class ChatDto {
    private int roomId;
    private int productId;
    private int productUserId;
    private int senderId;
    private String message;
}
