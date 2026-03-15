package com.example.demo.UtilCode.WebSocket;

import com.example.demo.UtilCode.MessageModel.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller

public class MessageSocketController {

        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @Autowired
        private KafkaTemplate<String, TextMessage> kafkaTemplate;

        //Слушаем, клиент отдает на send
        @MessageMapping("/chat/send")
        public void GetMessage(@Payload TextMessage textMessage) {
            String toUser = textMessage.getToUser();
            kafkaTemplate.send("user-mess", toUser, textMessage);
        }
        //Отдаем, клиент слушает на get
        public void SendMessage(String id, TextMessage textMessage) {
            messagingTemplate.convertAndSendToUser(
                    id,
                    "/chat/get",
                    textMessage);
        }
        @MessageMapping("/chat/addUser")
        public void addUser(@Payload TextMessage textMessage) {
            messagingTemplate.convertAndSendToUser(
                    textMessage.getToUser(),
                    "/queue/messages",
                    textMessage
            );
        }
    }

