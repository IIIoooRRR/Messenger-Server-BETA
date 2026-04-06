package com.example.demo.UnitModel.UserModel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    @Size(min = 3, max = 99, message = "длинна ник-нейма от 3 до 99 символов")
    String name;
    @NotNull
    @Size(min = 8, message = "пароль не менее 8 символов")
    String password;
}
