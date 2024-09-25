package com.example.kristp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "san_pham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name="id_danh_muc")
    private DanhMuc idDanhMuc;

    @ManyToOne
    @JoinColumn(name="id_chat_lieu")
    private ChatLieu idChatLieu;


    private String tenSanPham ;

    private String moTa;

    private Date ngayTao ;

    private Date ngaySua ;

    private Boolean trangThai ;
}


