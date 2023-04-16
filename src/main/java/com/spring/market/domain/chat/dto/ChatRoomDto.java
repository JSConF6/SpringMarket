package com.spring.market.domain.chat.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class ChatRoomDto {
    private int roomId;
    private int userId;
    private WebSocketSession session;
}
