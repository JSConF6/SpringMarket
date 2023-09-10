package com.spring.market.domain.product.dto;

import lombok.Data;

@Data
public class ProductListDto {
    private int id;
    private int user_id;
    private String name;
    private int category_id;
    private int place_id;
    private String content;
    private int price;
    private int read_count;
    private String order_status;
    private String create_at;
}
