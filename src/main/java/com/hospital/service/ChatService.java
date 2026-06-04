package com.hospital.service;

import com.hospital.entity.Message;

import java.util.List;

public interface ChatService {

    Message save(Message message);

    List<Message> getConversation(
            Long senderId,
            Long receiverId
    );

    void markAsRead(Long messageId);
}