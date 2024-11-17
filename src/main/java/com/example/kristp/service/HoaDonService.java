package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HoaDonService {

    Boolean taoHoaDon();
    Boolean thanhToanHoaDon(HoaDon hoaDon);
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);

    Page<HoaDon> getPaginationHoaDon(Integer pageNo , HoaDonStatus trangThai);
}
