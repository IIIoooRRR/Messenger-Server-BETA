package com.example.demo.ServerConfiguration.TomCat;

import com.example.demo.Exception.ChangeNullUser;
import com.example.demo.Exception.PasswordIsIncorrected;
import com.example.demo.Exception.UserNotFoundWithId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlers {
    @ExceptionHandler (UserNotFoundWithId.class)
    public ResponseEntity<?> handleNotFound(UserNotFoundWithId e) {
        log.warn("User not found {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler (ChangeNullUser.class)
    public ResponseEntity<?> changeNullUser(ChangeNullUser e) {
        log.error("Not Found User For CHANGE {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(PasswordIsIncorrected.class)
    public ResponseEntity<?> passwordIsIncorrected(PasswordIsIncorrected e) {
        log.warn("Password is incorrected {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
