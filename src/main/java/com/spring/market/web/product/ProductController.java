package com.spring.market.web.product;

import com.spring.market.domain.product.Product;
import com.spring.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}/detail")
    public String productDetail(@PathVariable("productId") int productId, Model model) {
        return "product/productDetail";
    }

    @GetMapping("/productEdit")
    public String productEdit() {
        return "product/productEdit";
    }

    @GetMapping("/productAdd")
    public String productAdd() {
        return "product/productAdd";
    }

}
