package com.example.demo.UnitModel.MessageModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;
@Entity
@Table(name = "text_message")
@Data
public class TextMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long mesId;
    @Size(min=1, max= 2000, message = "Сообщение вне лимита")
   private String textMessage;
   private Timestamp timestamp;
   @Column(nullable = false)
    @NotNull
   private String fromUser;
    @Column(nullable = false)
    @NotNull
    private String toUser;

}
