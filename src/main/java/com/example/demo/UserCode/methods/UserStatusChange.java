package com.example.demo.UserCode.methods;

import com.example.demo.UtilCode.Exception.UserNotFound;
import com.example.demo.UserCode.UserRepository.UsersRepository;
import com.example.demo.UserCode.UserModel.User;
import com.example.demo.UserCode.UserModel.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class UserStatusChange {
    @Autowired
    private UsersRepository usersRepository;

    public void setUserStatus(String id, UserStatus ConnectionType) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("Пользователь " + id + "не найден");
                    log.error("User not found: {}", id);
                    return new UserNotFound();
                });
        if (ConnectionType.equals(UserStatus.ONLINE)) {
            user.setUserStatus(UserStatus.ONLINE);
        } else {
            user.setUserStatus(UserStatus.OFFLINE);
        }
        usersRepository.save(user);
    }
}

