package com.spring.market.domain.product.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductUpdateDto {
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
    private String updateAt;
    private String deleteAt;
}
