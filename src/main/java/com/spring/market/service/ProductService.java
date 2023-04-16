package com.spring.market.service;

import com.spring.market.domain.product.Product;
import com.spring.market.domain.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
