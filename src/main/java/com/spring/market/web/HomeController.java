package com.spring.market.web;

import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        System.out.println(userService.test());
        return "index";
    }

    @GetMapping("/findId")
    public String findId() {
        return "findId";
    }

    @GetMapping("/findPw")
    public String findPw() {
        return "findPw";
    }
}
