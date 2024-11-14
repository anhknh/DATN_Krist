package com.example.kristp.service;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;

import java.util.ArrayList;
import java.util.List;

public interface GioHangChiTietService {
    ArrayList<GioHangChiTiet> getAllGioHangChiTiet(Integer idGioHang);
    void saveCartItem(GioHangChiTiet gioHangChiTiet);
    void deleteCartItem (GioHangChiTiet gioHangChiTiet);
    GioHangChiTiet getCartItemByCartItemId(Integer id);
    GioHangChiTiet getByCartAndProductDetail(GioHang gioHang, ChiTietSanPham chiTietSanPham);
    ArrayList<GioHangChiTiet> getAllGHCT();

    void increaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang);
    void decreaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang);
    // Lấy tất cả chi tiết giỏ hàng theo ID giỏ hàng
    List<GioHangChiTiet> getGioHangChiTietByGioHangId(Integer gioHangId);

    // Thêm mới một chi tiết giỏ hàng
    GioHangChiTiet addGioHangChiTiet(GioHangChiTiet gioHangChiTiet);

    // Cập nhật chi tiết giỏ hàng
    GioHangChiTiet updateGioHangChiTiet(GioHangChiTiet gioHangChiTiet);


    // Xoá chi tiết giỏ hàng theo ID
    void deleteGioHangChiTiet(Integer id);
}
