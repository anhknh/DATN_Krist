package com.example.kristp.service;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;

import java.util.List;

public interface KhachHangService {
    KhachHang getKHByUserId(TaiKhoan id);
    KhachHang saveKhachHang(String tenKhachHang, TaiKhoan taiKhoan, String sdtKh);
    List<KhachHang> getAllKhachHang();

    void deleteKhachHang(Integer id);
    void updateKhachHang(KhachHang kh);
}
