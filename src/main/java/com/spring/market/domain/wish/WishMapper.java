package com.spring.market.domain.wish;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WishMapper {
    int countByProductId(int productId);
}
