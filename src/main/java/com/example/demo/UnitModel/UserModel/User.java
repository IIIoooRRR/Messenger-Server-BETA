package com.example.demo.UnitModel.UserModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity
@Table(name = "UserData")
@Data

public class User {

    @Id

    @Column(nullable = false)
    private String id;

    @Column(nullable = true)
    private String avatarUrl;
    @Column(nullable = false, unique = true)
    @NotNull(message = "Имя обязательно")
    @Size(min = 3, max = 99, message = "Имя должно быть от 3 до 99 символов")
    private String name;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль не менее 8 символов")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Возраст обязателен")
    @Min(value = 1, message = "Возраст должен быть больше 0")
    @Max(value = 100, message = "Возраст должен быть меньше 120")
    private Integer age;
    @Column(nullable = true, name = "userStatus")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}