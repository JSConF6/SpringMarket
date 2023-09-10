package com.spring.market.domain.product.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductAddDto {
    private int userId;
    private int categoryId;
    private int placeId;
    private String name;
    private String content;
    private int price;
}
