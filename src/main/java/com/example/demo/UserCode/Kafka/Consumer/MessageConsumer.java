package com.example.demo.Kafka.Consumer;
import com.example.demo.UtilCode.WebSocket.MessageSocketController;
import com.example.demo.UtilCode.MessageModel.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    @Autowired
    private MessageSocketController messageSocketController;
    @KafkaListener(topics = "user-mess", groupId = "messenger group")
    public void GetMessage(TextMessage textMessage) {
        System.out.println("Кафка отправила сообщение: " + textMessage);
        messageSocketController.SendMessage(textMessage.getToUser(), textMessage);

    }

}