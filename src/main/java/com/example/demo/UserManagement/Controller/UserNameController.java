package com.example.demo.UserManagement.Controller;

import com.example.demo.Exception.UserNameIndexNotFound;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UserManagement.UserRepository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserNameController {
    @Autowired
    private UsersRepository usersRepository;
    @GetMapping("/get-name-user/{name}")
  public List<User> HttpGetAccount(@PathVariable String name) {
       try {
           return usersRepository.findByname(name);
       } catch (UserNameIndexNotFound e) {
           return null;
       }
    }
}
