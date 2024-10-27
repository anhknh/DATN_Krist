package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;

import java.util.List;

public interface HoaDonService {

    Boolean taoHoaDon();
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);
}
