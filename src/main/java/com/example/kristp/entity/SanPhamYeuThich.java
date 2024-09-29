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
@Table(name = "sanPhamYeuThich")
public class SanPhamYeuThich extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name = "idKhachHang")
    private KhachHang khachHang ;

    @ManyToOne
    @JoinColumn(name = "idSanPham")
    private SanPham sanPham ;
}
