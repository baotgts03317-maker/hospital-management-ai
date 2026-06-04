package com.hospital.controller;

import com.hospital.dto.ChatMessageDTO;
import com.hospital.entity.Message;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import com.hospital.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final UserRepository userRepository;

    private final ChatService chatService;

    @MessageMapping("/send-message")
    public void sendMessage(ChatMessageDTO dto) {

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow();

        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow();

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .messageContent(dto.getContent())
                .sentTime(LocalDateTime.now())
                .isRead(false)
                .build();

        Message savedMessage = chatService.save(message);

        messagingTemplate.convertAndSend(
                "/topic/messages/" + dto.getReceiverId(),
                savedMessage
        );
    }
}