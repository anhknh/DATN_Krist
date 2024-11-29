package com.example.kristp.service.impl;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;

    @Override
    public Page<HoaDonChiTiet> getHoaDonChiTietByHoaDon(HoaDon hoaDon, Pageable pageable) {
        return hoaDonChiTietRepo.getHoaDonChiTietByHoaDon(hoaDon, pageable);
    }

    @Override
    public List<HoaDonChiTiet> getHoaDonChiTietByHoaDon(HoaDon hoaDon) {
        return hoaDonChiTietRepo.getHoaDonChiTietByHoaDonList(hoaDon);
    }
}
