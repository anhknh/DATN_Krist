package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BanHangServiceImpl implements BanHangService {

    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;


    @Override
    public HoaDonChiTiet addGioHang(HoaDon hoaDon, Integer idChiTietSanPham, Integer soLuong) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(idChiTietSanPham);
        HoaDonChiTiet chiTiet = hoaDonChiTietRepo.findByHoaDonAndChiTietSanPham(hoaDon, chiTietSanPham);
        if (chiTiet == null) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPham);
            hoaDonChiTiet.setGiaTien(chiTietSanPham.getDonGia());
            hoaDonChiTiet.setSoLuong(soLuong);
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - soLuong);
            chiTietSanPhamRepository.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet);
        } else {
            chiTiet.setSoLuong(chiTiet.getSoLuong() + soLuong);
            chiTiet.setGiaTien(chiTietSanPham.getDonGia());
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - soLuong);
            chiTietSanPhamRepository.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(chiTiet);
        }

    }

    @Override
    public Float getTongTien(HoaDon hoaDon) {
        if (hoaDon == null) {
            return 0f;
        }
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepo.getHoaDonChiTietByHoaDon(hoaDon, null);
        float tongTien = 0f;
        for (HoaDonChiTiet chiTiet : hoaDonChiTietPage.getContent()) {
            tongTien = tongTien + chiTiet.getGiaTien();
        }
        return tongTien;
    }
}
