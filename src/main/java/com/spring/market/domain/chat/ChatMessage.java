package com.spring.market.domain.chat;

import lombok.Data;

@Data
public class ChatMessage {
    private int id;
    private int roomId;
    private int senderId;
    private String senderImageFileName;
    private String message;
    private String messageDate;
    private String createAt;
}
