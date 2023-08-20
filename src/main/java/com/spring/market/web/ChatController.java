package com.spring.market.web;

import com.spring.market.config.PrincipalDetails;
import com.spring.market.domain.chat.dto.ChatDetailDto;
import com.spring.market.domain.chat.dto.ChatMessageDto;
import com.spring.market.service.ChatService;
import com.spring.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping
    public String chat(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @RequestParam("productId") int productId,
                       Model model) {

        ChatDetailDto chatDetail = chatService.getChatDetail(productId, principalDetails.getMemberLoginDto().getId());
        List<ChatMessageDto> chatMessageList = chatService.getChatMessageList(chatDetail.getRoomId());

        model.addAttribute("chatDetail", chatDetail);
        model.addAttribute("chatMessageList", chatMessageList);
        return "chat/chat";
    }

    @GetMapping("/room")
    public String chatList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("chatRoomList", chatService.getChatRoomList(principalDetails.getMemberLoginDto().getId()));
        return "chat/chatList";
    }

    @GetMapping("/room/{roomId}")
    public String chatDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("roomId") int roomId,
                             Model model) {

        ChatDetailDto chatDetail = chatService.getChatRoomDetail(roomId, principalDetails.getMemberLoginDto().getId());
        List<ChatMessageDto> chatMessageList = chatService.getChatMessageList(chatDetail.getRoomId());

        model.addAttribute("chatDetail", chatDetail);
        model.addAttribute("chatMessageList", chatMessageList);
        return "chat/chat";
    }
}
