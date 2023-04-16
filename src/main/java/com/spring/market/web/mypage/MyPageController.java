package com.spring.market.web.mypage;

import com.spring.market.domain.user.User;
import com.spring.market.domain.user.dto.PasswordChangeDto;
import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;

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

    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@SessionAttribute("user") User user,
                                 @RequestBody PasswordChangeDto passwordChangeDto) {
        String result = userService.passwordUpdate(user, passwordChangeDto);
        return result;
    }

    @GetMapping("/passwordChangeSuccess")
    public String passwordChangeSuccess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/signin";
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public String withdraw(@SessionAttribute("user") User user,
                           @RequestParam String currentPassword) {
        String result = userService.withdraw(user, currentPassword);
        return result;
    }

    @GetMapping("/withdrawSuccess")
    public String withdrawSuccess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
