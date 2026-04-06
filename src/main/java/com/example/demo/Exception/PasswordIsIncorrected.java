package com.example.demo.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordIsIncorrected extends RuntimeException {
    public PasswordIsIncorrected(String id) {

        super("Пароль не корректен");
        log.info("Пользователь с id {} пытается войти по неправильному паролю", id );
    }
}
