package com.example.kristp.entity;

import com.example.kristp.enums.Status;
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
public class DanhGia extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name="id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name="id_san_pham")
    private SanPham sanPham;


    private Integer mucDoDanhGia ;

    private String noiDung;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

}


