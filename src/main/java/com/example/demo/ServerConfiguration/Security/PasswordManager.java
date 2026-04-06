package com.example.demo.ServerConfiguration.Security;

import com.example.demo.Exception.UserNotFoundWithId;
import com.example.demo.UnitModel.UserModel.User;
import com.example.demo.UserManagement.UserRepository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PasswordManager {
    @Autowired
    private UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }
        @Transactional
    public String create(String password) {
        return passwordEncoder.encode(password);
    }
    public Boolean compare(String password, String id) {
        User user = usersRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundWithId(id));
        return passwordEncoder.matches(password, user.getPassword());
    }
}