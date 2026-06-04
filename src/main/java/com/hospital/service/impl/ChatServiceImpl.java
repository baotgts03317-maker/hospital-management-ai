package com.hospital.service.impl;

import com.hospital.entity.Message;
import com.hospital.entity.User;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.MessageRepository;
import com.hospital.repository.UserRepository;
import com.hospital.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public Message save(Message message) {
        message.setSentTime(LocalDateTime.now());
        message.setIsRead(false);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getConversation(
            Long senderId,
            Long receiverId) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy người gửi"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy người nhận"));

        return messageRepository
                .findBySenderAndReceiverOrReceiverAndSenderOrderBySentTimeAsc(
                        sender,
                        receiver,
                        sender,
                        receiver
                );
    }

    @Override
    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy tin nhắn"));

        message.setIsRead(true);
        messageRepository.save(message);
    }
}