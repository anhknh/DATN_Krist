package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;

public interface BanHangService {
    HoaDonChiTiet addGioHang(HoaDon hoaDon, Integer idChiTietSanPham, String qrCode , Integer soLuong);

    Float getTongTien(HoaDon hoaDon);

    boolean addKhuyenMai(Integer idKhuyenMai, HoaDon hoaDon);
    boolean addKhachHang(Integer idKhachHang,String soDienThoai,String tenKhachHang, HoaDon hoaDon);
    boolean huyDonHang(Integer idHoaDon);

    Float findTongTienKhuyenMai(HoaDon hoaDon);
    boolean xoaSanPhamGioHang(Integer inHoaDonChiTiet);
    boolean updateSoLuongGioHang(Integer inHoaDonChiTiet, Integer idSoLuong);

}
