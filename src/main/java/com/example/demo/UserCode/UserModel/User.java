package com.example.demo.UserCode.UserModel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;


@Entity
@Table(name = "UserData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotNull(message = "Id не может быть пустым")

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "Номер обязателен")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Неверный формат номера")
    private String number;

    @Column(nullable = true)
    private String avatarUrl;

    @Column(nullable = false)
    @NotBlank(message = "Имя обязательно")
    @Size(min = 3, max = 99, message = "Имя должно быть от 3 до 99 символов")
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль не менее 8 символов")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Возраст обязателен")
    @Min(value = 1, message = "Возраст должен быть больше 0")
    @Max(value = 100, message = "Возраст должен быть меньше 120")
    private Integer age;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.OFFLINE;
    @Column(nullable = false)
    private Instant lastOnline = Instant.now();
}