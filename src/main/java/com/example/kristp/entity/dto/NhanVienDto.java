package com.example.kristp.entity.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NhanVienDto {
    // Thông tin nhân viên
    private Integer id;
    private String maNhanVien;
    private String tenNhanVien;
    private String soDienThoai;
    private Date ngaySinh;
    private String diaChi;

    // Thông tin tài khoản
    private String tenDangNhap;
    private String matKhau;
    private String email;
}
