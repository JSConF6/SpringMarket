package com.spring.market.web.product;

import com.spring.market.domain.product.Product;
import com.spring.market.domain.product.dto.ProductAddDto;
import com.spring.market.domain.product.dto.ProductListDto;
import com.spring.market.domain.product.dto.ProductUpdateDto;
import com.spring.market.dto.ResponseDto;
import com.spring.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String productAddView() {
        return "product/productAdd";
    }

    @PostMapping("/productAdd")
    @ResponseBody
    public ResponseEntity<?> productAdd(@ModelAttribute ProductAddDto productAddDto) {
        System.out.println(productAddDto);
        productService.productAdd(productAddDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 등록 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/productDelete")
    public ResponseEntity<?> productDelete(@RequestBody int id) {
        productService.productDelete(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 삭제 성공", null), HttpStatus.OK);
    }

    @PostMapping("/productUpdate")
    public ResponseEntity<?> productUpdate(@RequestBody ProductUpdateDto productUpdateDto) {
        productService.productUpdate(productUpdateDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 삭제 성공", null), HttpStatus.OK);
    }

    @GetMapping("/getProductList/{id}")
    public ResponseEntity<?> getProductList(@PathVariable int id){
        System.out.println(id);
        List<ProductListDto> product = productService.getProductList(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 삭제 성공", product), HttpStatus.OK);
    }
}
