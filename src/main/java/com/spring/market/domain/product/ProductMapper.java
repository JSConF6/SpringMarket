package com.spring.market.domain.product;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    Optional<Product> findById(int productId);
}
