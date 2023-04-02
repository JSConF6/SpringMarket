package com.spring.market.web.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping()
    public String mypage() {
        return "mypage/myPage";
    }

    @GetMapping("/profileEdit")
    public String profileEdit() {
        return "mypage/profileEdit";
    }

    @GetMapping("/wishList")
    public String widhList() {
        return "mypage/wishList";
    }

    @GetMapping("/mySaleList")
    public String mySaleList() {
        return "mypage/mySaleList";
    }

    @GetMapping("/myBuyList")
    public String myBuyList() {
        return "mypage/myBuyList";
    }

}
