package com.example.kristp.service;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;

import java.util.List;

public interface KhachHangService {
    KhachHang getKHByUserId(TaiKhoan id);
    void saveKhachHang(KhachHang kh);
    List<KhachHang> getAllKhachHang();

    void deleteKhachHang(Integer id);
    void updateKhachHang(KhachHang kh);
}
