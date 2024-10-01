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
    @JoinColumn(name = "id_mau")
    private MauSac mau;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "id_tay_ao")
    private TayAo tayAo;

    @ManyToOne
    @JoinColumn(name = "id_co_ao")
    private CoAo coAo;

    private Float donGia ;

    private Integer soLuong ;

    private String anhSanPham ;
}
