package com.spring.market.domain.alarm.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class AlarmUserDto {
    private int userId;
    private WebSocketSession session;
}
