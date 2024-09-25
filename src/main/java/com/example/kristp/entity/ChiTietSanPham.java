package com.example.kristp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "chiTietSanPham")
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name = "idMau")
    private MauSac mau;

    @ManyToOne
    @JoinColumn(name = "idSize")
    private Size size;

    private Float donGia ;

    private Integer soLuong ;

    private String anhSanPham ;
}
