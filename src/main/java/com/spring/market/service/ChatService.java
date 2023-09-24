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
        Product product = productMapper.findById(productId);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setProductId(productId);
        chatRoom.setBuyerId(buyerId);
        chatRoom.setProductUserId(product.getUserId());
        ChatRoom chatRoomDto = chatMapper.findByBuyerIdAndSellerIdAndProductId(chatRoom);

        if (chatRoomDto == null) {
            chatMapper.insertChatRoom(chatRoom);
        }

        ChatDetailDto chatDetail = chatMapper.findByRoomId(chatRoom.getId());
        if (chatDetail != null) {
            chatDetail.setNickname(chatDetail.getBuyerNickname());

            if (chatDetail.getBuyerId() == buyerId) {
                chatDetail.setNickname(chatDetail.getSellerNickname());
            }
        }

        return chatDetail;
    }

    public ChatDetailDto getChatRoomDetail(int roomId, int loginId) {
        ChatDetailDto chatDetailDto = chatMapper.findByRoomId(roomId);

        if (chatDetailDto != null) {
            File thumbnailImageFile = fileMapper.findThumbnailByProductId(chatDetailDto.getProductId());

            if (thumbnailImageFile != null) {
                chatDetailDto.setThumbnailImageName(thumbnailImageFile.getName());
            }

            if (chatDetailDto.getBuyerId() == loginId) {
                chatDetailDto.setNickname(chatDetailDto.getSellerNickname());

                return chatDetailDto;
            }

            chatDetailDto.setNickname(chatDetailDto.getBuyerNickname());
        }

        return chatDetailDto;
    }

    public List<ChatRoom> getChatRoomList(int userId) {
        List<ChatRoom> roomList = chatMapper.findAllById(userId);

        for (ChatRoom room : roomList) {
            File thumbnailImageFile = fileMapper.findThumbnailByProductId(room.getProductId());

            if (thumbnailImageFile != null) {
                room.setThumbnailFileName(thumbnailImageFile.getName());
            }

            if (room.getProductUserId() == userId) {
                setUserImageFile(room, room.getBuyerId());
                room.setNickname(room.getOrderUserNickname());
            } else {
                setUserImageFile(room, room.getProductUserId());
                room.setNickname(room.getProductUserNickname());
            }
        }

        return roomList;
    }

    private void setUserImageFile(ChatRoom room, int userId) {
        File userImageFile = fileMapper.findUserImageByUserId(userId);

        if (userImageFile != null) {
            room.setUserFileName(userImageFile.getName());
        }
    }

    public List<ChatMessageDto> getChatMessageList(int roomId) {
        List<ChatMessageDto> chatMessageDateList = chatMapper.findAllMessageDateByRoomId(roomId);
        List<ChatMessage> chatMessageList = chatMapper.findAllMessageByRoomId(roomId);

        if (!chatMessageDateList.isEmpty()) {
            chatMessageDateList.forEach((chatMessageDate) -> {
                List<ChatMessage> messageList = new ArrayList<>();
                chatMessageList.forEach((chatMessage) -> {
                    File senderImageFile = fileMapper.findUserImageByUserId(chatMessage.getSenderId());

                    if (chatMessageDate.getMessageDate().equals(chatMessage.getMessageDate())) {
                        if (senderImageFile != null) {
                            chatMessage.setSenderImageFileName(senderImageFile.getName());
                        }

                        messageList.add(chatMessage);
                    }
                });

                chatMessageDate.setChatMessage(messageList);
            });
        }

        return chatMessageDateList;
    }
}
