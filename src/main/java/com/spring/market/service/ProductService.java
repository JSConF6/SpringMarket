package com.spring.market.service;

import com.spring.market.domain.product.Product;
import com.spring.market.domain.product.ProductMapper;
import com.spring.market.domain.product.dto.ProductAddDto;
import com.spring.market.domain.product.dto.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductMapper productMapper;

    public List<Product> getProductList() {
        return productMapper.findAll();
    }

    public Product getProductDetail(int productId) {
        return productMapper.findById(productId).orElse(null);
    }

    @Transactional
    public void productAdd(ProductAddDto productAddDto) {
        try {
            productMapper.productAdd(productAddDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Transactional
    public void productDelete(int id) {
        productMapper.productDelete(id);
    }

    @Transactional
    public void productUpdate(ProductUpdateDto productUpdateDto) {
        productMapper.productUpdate(productUpdateDto);
    }

    public void getProductList(int id) {
        productMapper.getProductList(id);
    }
}
