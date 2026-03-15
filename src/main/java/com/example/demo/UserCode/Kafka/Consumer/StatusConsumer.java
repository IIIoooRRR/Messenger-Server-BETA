package com.example.demo.Kafka.Consumer;

import com.example.demo.UserCode.UserRepository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.UserCode.UserModel.UserStatusEvent;
import org.springframework.kafka.annotation.KafkaListener;


@Service
public class StatusConsumer {
    @Autowired
    private UsersRepository usersRepository;
    @KafkaListener(topics = "user-status", groupId = "messenger group")
    public void ReceiveStatusChange(UserStatusEvent event) {

    }

}
