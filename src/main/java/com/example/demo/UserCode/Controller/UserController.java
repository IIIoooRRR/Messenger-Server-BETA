package com.example.demo.UserCode.Controller;

import com.example.demo.UtilCode.Exception.UserNotFound;


import com.example.demo.UserCode.UserRepository.UsersRepository;
import com.example.demo.UserCode.UserModel.User;
import com.example.demo.UserCode.UserModel.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UsersRepository usersRepository;
    @GetMapping("/get-user/{id}")
 public User GetUser(@PathVariable String id) {
     return usersRepository.findById(id)
             .orElseThrow(()
                     -> new UserNotFound());
 }
    @PatchMapping("/update-user/{id}")
 public User UpdateUser(@PathVariable String id, @RequestBody @Valid User user) {
 User changeUser = usersRepository.findById(id)
                .orElseThrow(()
                    -> new UserNotFound());
 changeUser.setAvatarUrl(user.getAvatarUrl());
 changeUser.setPassword(user.getPassword());
 changeUser.setNumber(user.getNumber());
 changeUser.setAge(user.getAge());
 changeUser.setName(user.getName());


 return usersRepository.save(changeUser);
    }
   @PostMapping("/add/user")
 public User CreateUser(@RequestBody @Valid User user) {
        String id = DigestUtils.md5DigestAsHex(user.getNumber().getBytes(StandardCharsets.UTF_8));

        if(usersRepository.existsById(id)) {
            Random rand = new Random();
            int x = rand.nextInt(0, 1000);
            user.setId(id + "salt" + x);
        }
       user.setUserStatus(UserStatus.OFFLINE);
            return usersRepository.save(user);
   }

}