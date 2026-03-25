package com.example.demo.UserManagemeng.UserRepository;

import com.example.demo.UnitModel.UserModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {
    List<User> findByname(String name);
}