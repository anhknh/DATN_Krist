package com.example.kristp.entity;

import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "chat_lieu")
public class ChatLieu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotBlank
    private String tenChatLieu ;

    @NotBlank
    private String moTa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;


    @Override
    public String toString() {
        return "ChatLieu{" +
                "id=" + id +
                ", tenChatLieu='" + tenChatLieu + '\'' +
                ", moTa='" + moTa + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}

