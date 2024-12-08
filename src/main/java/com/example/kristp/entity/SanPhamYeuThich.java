package com.example.kristp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sanPhamYeuThich")
public class SanPhamYeuThich extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang ;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham ;
}
