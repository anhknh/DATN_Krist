package com.example.kristp.service.impl;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Override
    public Boolean taoHoaDon() {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNhanVien(nhanVienRepository.findById(1).orElse(null));
        hoaDon.setTrangThai(HoaDonStatus.HOA_DON_CHO);
        if(hoaDonRepository.countByTrangThai(HoaDonStatus.HOA_DON_CHO) < 5){
            hoaDonRepository.save(hoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean thanhToanHoaDon(HoaDon hoaDon) {
        if(hoaDon == null ) {
            return false;
        }
        hoaDon.setTrangThai(HoaDonStatus.DA_THANH_TOAN);
         hoaDonRepository.save(hoaDon);
         return true;
    }

    @Override
    public List<HoaDon> findAllHoaDonCho() {
        return hoaDonRepository.findByTrangThai(HoaDonStatus.HOA_DON_CHO);
    }

    @Override
    public HoaDon findHoaDonById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }
}
