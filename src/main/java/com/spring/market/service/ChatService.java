package com.spring.market.service;

import com.spring.market.domain.chat.ChatMapper;
import com.spring.market.domain.chat.ChatMessage;
import com.spring.market.domain.chat.ChatRoom;
import com.spring.market.domain.chat.dto.ChatDetailDto;
import com.spring.market.domain.chat.dto.ChatMessageDto;
import com.spring.market.domain.file.File;
import com.spring.market.domain.file.FileMapper;
import com.spring.market.domain.product.Product;
import com.spring.market.domain.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMapper chatMapper;
    private final ProductMapper productMapper;
    private final FileMapper fileMapper;

    public ChatRoom getChatRoom(int roomId) {
        return chatMapper.findById(roomId).orElse(null);
    }

    @Transactional
    public void createChatRoom(ChatRoom chatRoom) {
        chatMapper.insertChatRoom(chatRoom);
    }

    @Transactional
    public void createChatMessage(int roomId, int senderId, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setSenderId(senderId);
        chatMessage.setMessage(message);
        chatMapper.insertChatMessage(chatMessage);
    }

    public ChatDetailDto getChatDetail(int productId, int buyerId) {
        Product product = productMapper.findById(productId).orElse(null);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setProductId(productId);
        chatRoom.setBuyerId(buyerId);
        chatRoom.setProductUserId(product.getUserId());
        ChatRoom chatRoomDto = chatMapper.findByBuyerIdAndSellerIdAndProductId(chatRoom);

        ChatDetailDto chatDetail = null;
        if (chatRoomDto == null) {
            // 채팅방 생성
            chatMapper.insertChatRoom(chatRoom);
            chatDetail = chatMapper.findByRoomId(chatRoom.getId()).orElse(null);
        } else {
            chatDetail = chatMapper.findByRoomId(chatRoomDto.getId()).orElse(null);
        }

        if (chatDetail.getBuyerId() == buyerId) {
            chatDetail.setNickname(chatDetail.getSellerNickname());
        } else {
            chatDetail.setNickname(chatDetail.getBuyerNickname());
        }

        return chatDetail;
    }

    public ChatDetailDto getChatRoomDetail(int roomId, int loginId) {
        ChatDetailDto chatDetailDto = chatMapper.findByRoomId(roomId).orElse(null);

        if (chatDetailDto.getBuyerId() == loginId) {
            chatDetailDto.setNickname(chatDetailDto.getSellerNickname());
        } else {
            chatDetailDto.setNickname(chatDetailDto.getBuyerNickname());
        }

        return chatDetailDto;
    }

    public List<ChatRoom> getChatRoomList(int userId) {
        List<ChatRoom> roomList = chatMapper.findAllById(userId);

        for (ChatRoom room : roomList) {
            System.out.println(room);
            File thumbnailImageFile = fileMapper.findThumbnailByProductId(room.getProductId());
            System.out.println(thumbnailImageFile);
            if (thumbnailImageFile != null) {
                room.setThumbnailFileName(thumbnailImageFile.getName());
            }

            if (room.getProductUserId() == userId) {
                File orderUserImageFile = fileMapper.findUserImageByUserId(room.getBuyerId());
                room.setNickname(room.getOrderUserNickname());
                if (orderUserImageFile != null) {
                    room.setUserFileName(orderUserImageFile.getName());
                }
            } else {
                File productUserImageFile = fileMapper.findUserImageByUserId(room.getProductUserId());
                room.setNickname(room.getProductUserNickname());
                if (productUserImageFile != null) {
                    room.setUserFileName(productUserImageFile.getName());
                }
            }
        }

        return roomList;
    }

    public List<ChatMessageDto> getChatMessageList(int roomId) {
        List<ChatMessageDto> chatMessageDtoList = chatMapper.findAllMessageDateByRoomId(roomId);
        List<ChatMessage> chatMessageList = chatMapper.findAllMessageByRoomId(roomId);

        if (chatMessageDtoList.size() > 0) {
            for (ChatMessageDto chatMessageDto : chatMessageDtoList) {
                List<ChatMessage> tempList = new ArrayList<>();
                for (ChatMessage chatMessage : chatMessageList) {
                    if (chatMessageDto.getMessageDate().equals(chatMessage.getMessageDate())) {
                        tempList.add(chatMessage);
                    }
                }
                chatMessageDto.setChatMessage(tempList);
            }
        }

        return chatMessageDtoList;
    }
}
