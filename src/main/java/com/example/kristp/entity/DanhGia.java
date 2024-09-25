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
@Table(name = "danh_gia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name="id_khach_hang")
    private KhachHang idKhachHang;

    @ManyToOne
    @JoinColumn(name="id_san_pham")
    private SanPham idSanPham;


    private Integer mucDoDanhGia ;

    private String noiDung;

    private Date ngayTao ;

    private Date ngaySua ;

    private Boolean trangThai ;
}


