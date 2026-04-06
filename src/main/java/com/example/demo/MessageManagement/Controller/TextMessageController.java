package com.example.demo.MessageManagement.Controller;
import com.example.demo.Service.Message;
import com.example.demo.UnitModel.MessageModel.TextMessage;
import com.example.demo.UnitModel.UserModel.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class TextMessageController {
        @Autowired
        private Message message;
        //Получаем от клиента на send и в случае userStatus == Online сразу передаем
        @MessageMapping("/chat/send")
        public void getMessagee(@Valid @Payload TextMessage textMessage) {
               message.sendText(textMessage);
        }

        //Отдаем по определенному запросу, если юзер был offline, клиент слушает на get
        @MessageMapping("/chat/get")
        public void sendMessage(@Valid @Payload User user) {
               message.getText(user.getId());
        }
    }

