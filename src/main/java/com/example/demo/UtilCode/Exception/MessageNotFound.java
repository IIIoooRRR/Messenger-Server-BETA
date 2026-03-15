package com.example.demo.UtilCode.Exception;

import jakarta.servlet.annotation.HttpConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFound extends RuntimeException {
    public MessageNotFound() {
        super("Message not found\n ERROR 404");
    }
}
