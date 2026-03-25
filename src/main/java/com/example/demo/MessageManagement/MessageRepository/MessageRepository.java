package com.example.demo.MessageManagement.MessageRepository;

import com.example.demo.UnitModel.MessageModel.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<TextMessage, Long> {
    List<TextMessage> findByToUser(String toUser);
}
