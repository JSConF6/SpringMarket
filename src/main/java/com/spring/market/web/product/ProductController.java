package com.spring.market.web.product;

import com.spring.market.domain.product.dto.CategoryDto;
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
        System.out.println(productId);
        model.addAttribute("product",productService.getProductOne(productId));
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
    public ResponseEntity<?> productAdd(ProductAddDto productAddDto) {
        System.out.println(productAddDto);
        productService.productAdd(productAddDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 등록 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/productDelete")
    public ResponseEntity<?> productDelete(int id) {
        productService.productDelete(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 삭제 성공", null), HttpStatus.OK);
    }

    @PostMapping("/productUpdate")
    public ResponseEntity<?> productUpdate(@RequestBody ProductUpdateDto productUpdateDto) {
        productService.productUpdate(productUpdateDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 수정 성공", null), HttpStatus.OK);
    }

    @GetMapping("/getProductList/{id}")
    public ResponseEntity<?> getProductList(@PathVariable int id){
        List<ProductListDto> product = productService.getProductList(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "상품 리스트 불러오기 성공", product), HttpStatus.OK);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategory(){
        List<CategoryDto> categoryDtos = productService.getCategory();

        return new ResponseEntity<>(new ResponseDto<>(1, "카테고리 리스트 조회 성공", categoryDtos), HttpStatus.OK);
    }

    @PostMapping("/sailComplete")
    public ResponseEntity<?> sailComplete(int id){
        productService.sailComplete(id);

        return new ResponseEntity<>(new ResponseDto<>(1, "상품 상태 변경완료", null), HttpStatus.OK);
    }

}
