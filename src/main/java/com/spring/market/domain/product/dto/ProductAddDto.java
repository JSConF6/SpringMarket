package com.spring.market.domain.product.dto;

import lombok.Data;

@Data
public class ProductAddDto {
    private int productId;
    private int userId;
    private String nickname;
    private int categoryId;
    private int placeId;
    private String title;
    private String content;
    private int price;
    private int readCount;
    private String orderStatus;
    private String createAt;
}
