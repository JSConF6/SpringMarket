package com.spring.market.domain.chat.dto;

import lombok.Data;

@Data
public class ChatDetailDto {
    private int roomId;
    private int productId;
    private int sellerId;
    private String sellerNickname;
    private int buyerId;
    private String buyerNickname;
    private String nickname;
    private String title;
    private int price;
    private String path;
    private String createAt;

    private String thumbnailImageName;
}
