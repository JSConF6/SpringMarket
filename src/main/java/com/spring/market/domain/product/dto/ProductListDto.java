package com.spring.market.domain.product.dto;

import lombok.Data;

@Data
public class ProductListDto {
    private int id;
    private int userId;
    private int categoryId;
    private int placeId;
    private String name;
    private String content;
    private int price;
    private int readCount;
    private String orderStatus;
    private String delYn;
    private String createAt;
    private String updateAt;
    private String deleteAt;
}
