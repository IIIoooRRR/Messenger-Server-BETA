package com.example.demo.Service;

import com.example.demo.Exception.PasswordIsIncorrected;
import com.example.demo.Exception.UserNameIndexNotFound;
import com.example.demo.Exception.UserNotFoundWithId;
import com.example.demo.Exception.UsernameExists;
import com.example.demo.ServerConfiguration.Security.PasswordManager;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UnitModel.UserModel.UserStatus;
import com.example.demo.UserManagement.UserRepository.UsersRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class Account {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordManager passwordManager;
    @Transactional
    public User create(@Valid User user) {
    user.setId(UUID.randomUUID().toString());
    user.setPassword(passwordManager.create(user.getPassword()));
    user.setUserStatus(UserStatus.OFFLINE);
    usersRepository.save(user);
           return getUser(user.getId());
    }

    @Transactional
    public User changeUser(@Valid User changeUser) {
        User oldUser = getUser(changeUser.getId());

        if (!passwordManager.compare(changeUser.getPassword(), oldUser.getPassword())) {
        throw new PasswordIsIncorrected(changeUser.getId());
        }

        if (!oldUser.getNickname().equals(changeUser.getNickname())) {
            List<User> haveName = usersRepository.findByNickname(changeUser.getNickname());
            if (!haveName.isEmpty()) {
                throw new UsernameExists(changeUser.getNickname());
            }
        }
        changeUser.setPassword(passwordManager.create(changeUser.getPassword()));
        return usersRepository.save(changeUser);
    }


    public User getUser(String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundWithId(id));
    }




    public void changeUserStatus(String id, UserStatus сonnectionType) {
        User user = getUser(id);
        if (сonnectionType.equals(UserStatus.ONLINE)) {
            user.setUserStatus(UserStatus.ONLINE);
        } else {
            user.setUserStatus(UserStatus.OFFLINE);
        }
        usersRepository.save(user);
    }

    public User loginUser(String name, String password) {

        List<User> userList = usersRepository.findByNickname(name);
        if (userList.isEmpty()) {
            throw new UserNameIndexNotFound(name);
        }
        User user = userList.getFirst();
        if (!passwordManager.compare(password, user.getPassword())) {
            throw new PasswordIsIncorrected(name);
        }
        return user;
    }
}
