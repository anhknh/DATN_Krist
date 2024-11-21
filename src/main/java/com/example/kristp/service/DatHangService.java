package com.example.kristp.service;

import com.example.kristp.entity.GioHangChiTiet;

import java.util.List;

public interface DatHangService {

    public boolean datHangOnline(List<GioHangChiTiet> gioHangChiTietList, Integer idDiaChi, Integer idKhuyenMai, String phuongThucThanhToan, Float tongTien);
}
