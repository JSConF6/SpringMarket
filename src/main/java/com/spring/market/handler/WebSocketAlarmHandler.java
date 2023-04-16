package com.spring.market.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.market.domain.alarm.Alarm;
import com.spring.market.domain.alarm.dto.AlarmDto;
import com.spring.market.domain.alarm.dto.AlarmUserDto;
import com.spring.market.domain.user.User;
import com.spring.market.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class WebSocketAlarmHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final AlarmService alarmService;

    private static List<AlarmUserDto> sessions = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        AlarmDto alarmDto = objectMapper.readValue(payload, AlarmDto.class);

        Alarm alarm = new Alarm();
        alarm.setUserId(alarmDto.getSellerId());
        alarm.setType("CHAT");
        alarm.setUrl("/chat/" + alarmDto.getRoomId());
        alarm.setMessage(alarmDto.getMessage());

        alarmService.saveAlarm(alarm);

        for (AlarmUserDto alarmUserDto : sessions) {
            if (alarmUserDto.getUserId() == alarmDto.getSellerId()) {
                alarmUserDto.getSession().sendMessage(new TextMessage(alarm.getId() + "," + alarm.getUrl() + "," + alarmDto.getMessage()));
            }
        }
    }

    // Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        int userId = getId(session);

        AlarmUserDto alarmUserDto = new AlarmUserDto();
        alarmUserDto.setUserId(userId);
        alarmUserDto.setSession(session);

        sessions.add(alarmUserDto);
    }

    // Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (int i = 0; i < sessions.size(); i++) {
            AlarmUserDto alarmUserDto = sessions.get(i);

            if (alarmUserDto.getSession().equals(session)) {
                sessions.remove(i);
                break;
            }
        }
    }

    private int getId(WebSocketSession session) {
        Map<String, Object> map = session.getAttributes();
        User user = (User) map.get("user");
        return user.getId();
    }
}
