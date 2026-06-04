package com.hospital.repository;

import com.hospital.entity.Message;
import com.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderBySentTimeAsc(
            User sender,
            User receiver,
            User receiver2,
            User sender2
    );
}