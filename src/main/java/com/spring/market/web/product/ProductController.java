package com.spring.market.web.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/productDetail")
    public String productDetail() {
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
