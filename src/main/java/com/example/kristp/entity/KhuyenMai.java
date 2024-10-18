package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private String kieuKhuyenMai;
    private Float giaTri;
    private Float mucGiamToiDa;

    private String trangThai;

    private Date ngayBatDau;
    private Date ngayKetThuc;


}
