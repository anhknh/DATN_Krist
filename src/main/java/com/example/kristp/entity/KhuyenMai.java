package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotBlank
    private String maKhuyenMai;
    @NotBlank
    private String tenKhuyenMai;
    @NotNull
    private Boolean kieuKhuyenMai;
    @NotNull
    private Float giaTri;
    @NotNull
    private Float mucGiamToiDa;

    @NotBlank
    private String trangThai;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayBatDau;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayKetThuc;


}
