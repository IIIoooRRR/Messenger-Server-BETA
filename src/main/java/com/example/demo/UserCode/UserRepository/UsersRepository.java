package com.example.demo.UserCode.UserRepository;

import com.example.demo.UserCode.UserModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {

}