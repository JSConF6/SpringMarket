package com.spring.market.domain.product;

import com.spring.market.domain.product.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    Product findById(int productId);

    int productAdd(ProductAddDto productAddDto);

    void productDelete(int id);

    void productUpdate(ProductUpdateDto productUpdateDto);

    List<ProductListDto> getProductList(int id);

    List<CategoryDto> getCategory();

    void productImageAdd (ProductImageDto productImageDto);

    void sailComplete(int id);
}
