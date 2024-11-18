package com.example.kristp.service;

import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;

import java.util.List;

public interface GioHangService {
    GioHang findGioHangByKhachHangId(KhachHang khachHang);
    boolean addSanPhamToGioHang(Integer idCTSP, Integer soLuong);
    void deleteGioHangById(List<Integer> id);

    Integer countCartItem();
}
