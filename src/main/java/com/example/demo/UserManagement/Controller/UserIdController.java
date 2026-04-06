package com.example.demo.UserManagement.Controller;
import com.example.demo.UnitModel.UserModel.LoginDTO;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.Service.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Slf4j
@RestController
public class UserIdController {
    @Autowired
    private Account account;
    @GetMapping("get/user/{id}")
 public ResponseEntity<User> httpGetAccount(@PathVariable String id) {
        return ResponseEntity.ok(account.getUser(id));
 }
    @PatchMapping("update/user/{id}")
 public ResponseEntity<User> httpUserChange(@PathVariable String id, @RequestBody @Valid User changeUser) {
        return ResponseEntity.ok(account.changeUser(changeUser));
    }
    @GetMapping("/login/user")
 public ResponseEntity<User> httpLoginAccount(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(account.loginUser(loginDTO.getName(), loginDTO.getPassword()));
    }

   @PostMapping("/auth/user")
 public ResponseEntity<User> httpCreateAccount(@RequestBody @Valid User user) {
        return ResponseEntity.ok(account.create(user));
   }
}