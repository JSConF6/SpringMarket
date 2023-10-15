package com.spring.market.domain.product;

import lombok.Data;

@Data
public class Product {
    private int productId;
    private int userId;
    private String nickname;
    private int categoryId;
    private int placeId;
    private String name;
    private String content;
    private int price;
    private int readCount;
    private String orderStatus;
    private String createAt;

    private String thumbnailImageName;
    private int wishCount;
}
