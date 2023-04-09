package com.spring.market.web;

import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
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

    @GetMapping("/chatList")
    public String chatList() {
        return "chat/chatList";
    }

    @GetMapping("/chat/{otherUserId}")
    public String chat(@PathVariable("otherUserId") int otherUserId) {
        return "chat/chat";
    }
}
