package com.example.kristp.service;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;

import java.util.ArrayList;

public interface GioHangChiTietService {
    ArrayList<GioHangChiTiet> getAllGioHangChiTiet(Integer idGioHang);
    void saveCartItem(GioHangChiTiet gioHangChiTiet);
    void deleteCartItem (GioHangChiTiet gioHangChiTiet);
    GioHangChiTiet getCartItemByCartItemId(Integer id);
    GioHangChiTiet getByCartAndProductDetail(GioHang gioHang, ChiTietSanPham chiTietSanPham);

    void increaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang);
    void decreaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang);



}
