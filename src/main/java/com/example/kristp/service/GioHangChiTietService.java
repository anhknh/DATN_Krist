package com.example.kristp.service;

import com.example.kristp.entity.GioHangChiTiet;

import java.util.ArrayList;
import java.util.List;

public interface GioHangChiTietService {
    ArrayList<GioHangChiTiet> getAllGHCT();

    // Lấy tất cả chi tiết giỏ hàng theo ID giỏ hàng
    List<GioHangChiTiet> getGioHangChiTietByGioHangId(Integer gioHangId);

    // Thêm mới một chi tiết giỏ hàng
    GioHangChiTiet addGioHangChiTiet(GioHangChiTiet gioHangChiTiet);

    // Cập nhật chi tiết giỏ hàng
    GioHangChiTiet updateGioHangChiTiet(GioHangChiTiet gioHangChiTiet);


    // Xoá chi tiết giỏ hàng theo ID
    void deleteGioHangChiTiet(Integer id);
}
