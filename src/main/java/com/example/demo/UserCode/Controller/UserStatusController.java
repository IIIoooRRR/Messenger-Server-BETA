package com.example.demo.UserCode.Controller;

import com.example.demo.UtilCode.Exception.UserNotFound;
import com.example.demo.UserCode.UserRepository.UsersRepository;
import com.example.demo.UserCode.UserModel.User;
import com.example.demo.UserCode.UserModel.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class UserStatusController {
   @Autowired
    private UsersRepository usersRepository;

    public User UserOnline(String id) {
        User user = usersRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotFound());
        user.setUserStatus(UserStatus.ONLINE);
        return usersRepository.save(user);
    }
    public User UserOffline(String id) {
        User user = usersRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotFound());
        user.setLastOnline(Instant.now());
        user.setUserStatus(UserStatus.OFFLINE);
        return usersRepository.save(user);
    }
    public User UserIDLE(String id) {
        User user = usersRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotFound());
        user.setUserStatus(UserStatus.IDLE);
        return usersRepository.save(user);
    }
}
