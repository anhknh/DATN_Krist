package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;

import java.util.List;

public interface HoaDonService {

    Boolean taoHoaDon();
    Boolean thanhToanHoaDon(HoaDon hoaDon);
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);
}
