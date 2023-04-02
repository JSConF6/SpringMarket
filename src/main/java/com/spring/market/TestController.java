package com.spring.market;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String signin() {
        return "auth/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @GetMapping("/findId")
    public String findId() {
        return "findId";
    }

    @GetMapping("/findPw")
    public String findPw() {
        return "findPw";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/product/productDetail")
    public String productDetail() {
        return "product/productDetail";
    }

    @GetMapping("/product/productEdit")
    public String productEdit() {
        return "product/productEdit";
    }

    @GetMapping("/product/productAdd")
    public String productAdd() {
        return "product/productAdd";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage/myPage";
    }

    @GetMapping("/mypage/profileEdit")
    public String profileEdit() {
        return "mypage/profileEdit";
    }

    @GetMapping("/mypage/wishList")
    public String widhList() {
        return "mypage/wishList";
    }

    @GetMapping("/mypage/mySaleList")
    public String mySaleList() {
        return "mypage/mySaleList";
    }

    @GetMapping("/mypage/myBuyList")
    public String myBuyList() {
        return "mypage/myBuyList";
    }
}
