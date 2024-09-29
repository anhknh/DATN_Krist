package com.example.kristp.service;

import com.example.kristp.entity.TaiKhoan;

import java.util.ArrayList;

public interface TaiKhoanService {
    ArrayList<TaiKhoan> getTaiKhoan();
    TaiKhoan getTenDangNhap(String tenDangNhap);
    TaiKhoan getUserId(Integer id);
    TaiKhoan dangNhap(String tenDangNhap, String matKhau);


}
