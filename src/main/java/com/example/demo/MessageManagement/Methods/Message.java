package com.example.demo.MessageManagement.Methods;

import com.example.demo.Exception.UserNotFoundWithId;
import com.example.demo.MessageManagement.MessageRepository.MessageRepository;
import com.example.demo.UnitModel.MessageModel.TextMessage;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UnitModel.UserModel.UserStatus;
import com.example.demo.UserManagemeng.Methods.Account;
import com.example.demo.UserManagemeng.UserRepository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Message {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private Account account;
    public void sendText(@Payload TextMessage textMessage) {
        User user = account.getUser(textMessage.getToUser());
        if (user.getUserStatus().equals(UserStatus.ONLINE) || user.getUserStatus().equals(UserStatus.IDLE)) {
            messagingTemplate.convertAndSendToUser(textMessage.getToUser(), "/queue/messages", textMessage);
        } else {
            messageRepository.save(textMessage);
        }
    }
    @Transactional
    public void getText(String toUser) {
        if (usersRepository.existsById(toUser)) {
            List<TextMessage> messageToUser = messageRepository.findByToUser(toUser);
                if(messageToUser.isEmpty()) {
                    return;
                }
            messagingTemplate.convertAndSendToUser(toUser, "/queue/messages", messageToUser);
                messageRepository.deleteAll(messageToUser);
        } else {
            throw new UserNotFoundWithId(toUser);
        }
    }
}
