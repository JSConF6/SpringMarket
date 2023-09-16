package com.spring.market.domain.product;

import com.spring.market.domain.product.dto.CategoryDto;
import com.spring.market.domain.product.dto.ProductAddDto;
import com.spring.market.domain.product.dto.ProductListDto;
import com.spring.market.domain.product.dto.ProductUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    Optional<Product> findById(int productId);

    void productAdd(ProductAddDto productAddDto);

    void productDelete(int id);

    void productUpdate(ProductUpdateDto productUpdateDto);

    List<ProductListDto> getProductList(int id);

    List<CategoryDto> getCategory();
}
