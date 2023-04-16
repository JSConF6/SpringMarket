package com.spring.market.domain.chat;

import com.spring.market.domain.chat.dto.ChatDetailDto;
import com.spring.market.domain.chat.dto.ChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ChatMapper {

    Optional<ChatRoom> findById(int id);

    Optional<ChatDetailDto> findByRoomId(int roomId);

    void insertChatRoom(ChatRoom chatRoom);

    List<ChatRoom> findAllById(int userId);

    ChatRoom findByBuyerIdAndSellerIdAndProductId(ChatRoom chatRoom);

    void insertChatMessage(ChatMessage chatMessage);

    List<ChatMessage> findAllMessageByRoomId(int roomId);

    List<ChatMessageDto> findAllMessageDateByRoomId(int roomId);
}
