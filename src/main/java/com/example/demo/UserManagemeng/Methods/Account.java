package com.example.demo.UserManagemeng.Methods;

import com.example.demo.Exception.ChangeNullUser;
import com.example.demo.Exception.UserNotFoundWithId;
import com.example.demo.Exception.UsernameExists;
import com.example.demo.UnitModel.UserModel.NameIndex;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UnitModel.UserModel.UserStatus;
import com.example.demo.UserManagemeng.UserRepository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;


@Component
public class Account {
    @Autowired
    private UsersRepository usersRepository;
    Random rand = new Random();
    @Transactional
    public User create(@Valid User user) {
    String id = DigestUtils.md5DigestAsHex(user.getName().getBytes(StandardCharsets.UTF_8));
            if (usersRepository.existsById(id)) {
                throw new UsernameExists(user.getName());
    }
                user.setId(id);
            user.setUserStatus(UserStatus.OFFLINE);
            usersRepository.save(user);
           return getUser(user.getId());
    }


    @Transactional
    public User changeUser(@Valid User changeUser) {
        User oldUser = getUser(changeUser.getId());
        if (!oldUser.getName().equals(changeUser.getName())) {
            List<User> haveName = usersRepository.findByname(changeUser.getName());
            if (!haveName.isEmpty()) {
                throw new UsernameExists(changeUser.getName());
            }
        }
        String id = DigestUtils.md5DigestAsHex(changeUser.getName().getBytes(StandardCharsets.UTF_8));
        changeUser.setId(id);
        usersRepository.deleteById(oldUser.getId());
        return usersRepository.save(changeUser);
    }


    public User getUser( String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundWithId(id));
    }


        @Transactional
    public void changeUserStatus(String id, UserStatus ConnectionType) {
        User user = getUser(id);
        if (ConnectionType.equals(UserStatus.ONLINE)) {
            user.setUserStatus(UserStatus.ONLINE);
        } else {
            user.setUserStatus(UserStatus.OFFLINE);
        }
        usersRepository.save(user);
    }
}
