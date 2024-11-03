package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;

public interface BanHangService {
    HoaDonChiTiet addGioHang(HoaDon hoaDon, Integer idChiTietSanPham, String qrCode , Integer soLuong);

    Float getTongTien(HoaDon hoaDon);
}
