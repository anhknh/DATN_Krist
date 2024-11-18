package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HoaDonService {

    Boolean taoHoaDon();
    Boolean thanhToanHoaDon(HoaDon hoaDon);
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);
    List<HoaDon> getAllHoaDon(String trangThai);
    List<HoaDon> timKiemHoaDon(Integer id, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc);
    Map<HoaDonStatus, Long> getCountByTrangThai();
}
