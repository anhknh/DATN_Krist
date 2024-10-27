package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HoaDonChiTietService {

    Page<HoaDonChiTiet> getHoaDonChiTietByHoaDon(HoaDon hoaDon, Pageable pageable);
}
