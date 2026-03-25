package com.example.demo.UserManagemeng.Controller;

import com.example.demo.Exception.UserNotFoundWithId;
import com.example.demo.Exception.UsernameExists;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UserManagemeng.Methods.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RestController
public class UserIdController {
    @Autowired
    private Account account;
    private final ExecutorService ACCOUNT_POOL = Executors.newVirtualThreadPerTaskExecutor();
    @GetMapping("/get-id-user/{id}")
 public CompletableFuture<ResponseEntity<User>> httpGetAccount(@PathVariable String id) {
        return CompletableFuture.supplyAsync(
                () -> account.getUser(id), ACCOUNT_POOL)
                .thenApply(ResponseEntity::ok)
                .exceptionally(
                        throwable -> {
                           Throwable cause = throwable.getCause();
                           if (cause instanceof UserNotFoundWithId) {
                               return ResponseEntity.notFound().build();
                           }
                           log.error("Unkown error {}", cause);
                           return ResponseEntity.internalServerError().build();
                           });
 }

    @PatchMapping("/update-user/{id}")
 public CompletableFuture<ResponseEntity<User>> httpUserChange(@PathVariable String id, @RequestBody @Valid User changeUser) {
        return CompletableFuture.supplyAsync(()
                                    -> account.changeUser(changeUser), ACCOUNT_POOL)
                            .thenApply(ResponseEntity::ok)
                            .exceptionally(
                                        throwable -> {
                                            Throwable cause = throwable.getCause();
                                            if (cause instanceof UserNotFoundWithId) {
                                                return ResponseEntity.notFound().build();
                                            }
                                            if (cause instanceof UsernameExists) {
                                                return ResponseEntity.badRequest().build();
                                            }
                                            log.error("Unkown error {}", cause);
                                            return ResponseEntity.internalServerError().build();
                                        });
    }


   @PostMapping("/add/user")
 public CompletableFuture<ResponseEntity<User>> httpCreateAccount(@RequestBody @Valid User user) {
        return CompletableFuture.supplyAsync(()
                        -> account.create(user), ACCOUNT_POOL)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    Throwable cause = throwable.getCause();
                    if (cause instanceof UsernameExists) {
                        return ResponseEntity.badRequest().build();
                    }
                    log.error("Unkown error {}", cause);
                    return ResponseEntity.internalServerError().build();
                });
   }
}