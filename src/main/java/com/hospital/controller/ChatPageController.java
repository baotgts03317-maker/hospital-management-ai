package com.hospital.controller;

import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import com.hospital.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatPageController {

    private final UserRepository userRepository;

    private final ChatService chatService;

    @GetMapping("/{senderId}/{receiverId}")
    public String chatPage(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            Model model) {

    	User sender = userRepository.findById(senderId)
    	        .orElseThrow(() ->
    	            new RuntimeException("Không tìm thấy Sender"));

    	User receiver = userRepository.findById(receiverId)
    	        .orElseThrow(() ->
    	            new RuntimeException("Không tìm thấy Receiver"));

        model.addAttribute("sender", sender);

        model.addAttribute("receiver", receiver);

        model.addAttribute(
                "messages",
                chatService.getConversation(
                        senderId,
                        receiverId
                )
        );

        return "chat/chat";
    }
}