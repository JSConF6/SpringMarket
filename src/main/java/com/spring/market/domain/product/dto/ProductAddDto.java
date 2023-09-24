package com.spring.market.domain.product.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductAddDto {
    private int id;
    private int userId;
    private int categoryId;
    private int placeId;
    private String name;
    private String content;
    private int price;
    private List<MultipartFile> imageFiles;
}
