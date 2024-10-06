package com.example.kristp.entity;

import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "san_pham")
public class SanPham extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name="id_danh_muc")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name="id_chat_lieu")
    private ChatLieu chatLieu;

    @OneToMany(mappedBy = "sanPham",cascade = CascadeType.ALL)
    private List<ChiTietSanPham> chiTietSanPham;


    private String tenSanPham ;

    private String moTa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

}


