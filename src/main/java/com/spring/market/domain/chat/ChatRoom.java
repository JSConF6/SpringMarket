package com.spring.market.domain.chat;

import lombok.Data;

@Data
public class ChatRoom {
    private int id;
    private int productId;
    private int productUserId;
    private String productUserNickname;
    private int buyerId;
    private String orderUserNickname;
    private String nickname;
    private String message;
    private String createAt;
}
