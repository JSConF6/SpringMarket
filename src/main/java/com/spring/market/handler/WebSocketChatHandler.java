package com.spring.market.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.market.domain.chat.dto.ChatDto;
import com.spring.market.domain.chat.dto.ChatRoomDto;
import com.spring.market.domain.user.User;
import com.spring.market.service.ChatService;
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
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    private static List<ChatRoomDto> sessions = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        int userId = getId(session);
        System.out.println("payload : " + payload);

        ChatDto chatDto = objectMapper.readValue(payload, ChatDto.class);

        chatService.createChatMessage(chatDto.getRoomId(), userId, chatDto.getMessage());

        for (ChatRoomDto chatRoomDto : sessions) {
            if (chatDto.getRoomId() == chatRoomDto.getRoomId()) {
                if (userId != chatRoomDto.getUserId()) {
                    chatRoomDto.getSession().sendMessage(new TextMessage(chatDto.getMessage()));
                }
            }
        }
    }

    // Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        String[] pathSplit = path.split("/");

        int roomId = Integer.parseInt(pathSplit[3]);
        int userId = getId(session);

        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setRoomId(roomId);
        chatRoomDto.setUserId(userId);
        chatRoomDto.setSession(session);

        sessions.add(chatRoomDto);
    }

    // Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (int i = 0; i < sessions.size(); i++) {
            ChatRoomDto chatRoomDto = sessions.get(i);

            if (chatRoomDto.getSession().equals(session)) {
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
