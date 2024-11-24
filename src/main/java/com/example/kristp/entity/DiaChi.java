package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "dia_chi")
public class DiaChi extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sdt;

    private String diaChi;

    private String tenKhachHang;
    private String maTinhThanh;
    private String tenTinhThanh;
    private String maQuanHuyen;
    private String tenQuanHuyen;
    private String maPhuongXa;
    private String tenPhuongXa;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
}
