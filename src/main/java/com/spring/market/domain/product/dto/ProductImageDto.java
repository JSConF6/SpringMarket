package com.spring.market.domain.product.dto;

import lombok.Data;

@Data
public class ProductImageDto {
    private int productId;
    private int productImageId;
    private String thumbnail;

}
