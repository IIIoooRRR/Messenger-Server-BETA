package com.example.demo.UtilCode.MessageModel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessage {
   private Long mesId;
    @Size(min=1, max= 2000, message = "Сообщение вне лимита")
   private String textMessage;
   private Timestamp timestamp;
    @NotNull
   private String fromUser;
    @NotNull
   private String toUser;
   private boolean delivered;
    private boolean received;

}
