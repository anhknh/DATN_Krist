package com.example.kristp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chi_tiet_san_pham")
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
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

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;


    private Float donGia ;

    private Integer soLuong ;

    private String anhSanPham ;
    private String qrCode;
}
