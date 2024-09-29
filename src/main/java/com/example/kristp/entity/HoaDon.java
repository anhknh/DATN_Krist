package com.example.kristp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoa_don")
public class HoaDon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_dia_chi")
    private DiaChi diaChi;
    @Column(name = "ngay_dat_hang")
    private Date ngayDatHang;
    @Column(name = "tong_tien")
    private BigDecimal tongTien;
    @Column(name = "trang_thai")
    private String trangThai;
}