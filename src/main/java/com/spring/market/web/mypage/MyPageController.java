package com.spring.market.web.mypage;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.user.User;
import com.spring.market.domain.user.dto.LoginDto;
import com.spring.market.domain.user.dto.PasswordChangeDto;
import com.spring.market.domain.user.dto.ProfileEditDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import com.spring.market.dto.ResponseDto;
import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final Logger logger = LoggerFactory.getLogger(MyPageController.class);

    private final UserService userService;

    @GetMapping()
    public String mypage() {
        return "mypage/myPage";
    }

    @GetMapping("/profile/edit")
    public String profileEditView(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model) {
        LoginDto member = principalDetails.getMemberLoginDto();

        UserInfoDto userInfo = userService.getUserInfo(member);

        model.addAttribute("userInfo", userInfo);

        return "mypage/profileEdit";
    }

    @PostMapping("/profile/edit")
    public String profileEdit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      ProfileEditDto profileEditDto) {
        LoginDto member = principalDetails.getMemberLoginDto();
        profileEditDto.setId(member.getId());
        profileEditDto.setUsername(member.getLogin_id());

        userService.updateProfile(profileEditDto);

        return "redirect:/mypage/profile/edit";
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
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody PasswordChangeDto passwordChangeDto) {
        userService.passwordUpdate(principalDetails.getMemberLoginDto(), passwordChangeDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "비밀번호 변경 성공", null), HttpStatus.OK);
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
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal PrincipalDetails principalDetails,
                           @RequestParam String currentPassword) {
        userService.withdraw(principalDetails.getMemberLoginDto(), currentPassword);
        return new ResponseEntity<>(new ResponseDto<>(1, "탈퇴 성공", null), HttpStatus.OK);
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
