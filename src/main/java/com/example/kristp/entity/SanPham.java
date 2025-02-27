package com.example.kristp.entity;

import com.example.kristp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonIgnore
    private List<ChiTietSanPham> chiTietSanPham;

    private String tenSanPham ;

    private String moTa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

}


