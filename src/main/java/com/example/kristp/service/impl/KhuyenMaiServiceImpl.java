package com.example.kristp.service.impl;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.MauSac;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.KhuyenMaiRepository;
import com.example.kristp.service.KhuyenMaiService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Override
    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        return (ArrayList<KhuyenMai>) khuyenMaiRepository.findAll();
    }

    @Override
    public KhuyenMai getKhuyenMaiById(int id) {
        return khuyenMaiRepository.findById(id).get();
    }

    @Override
    public KhuyenMai addKhuyenMai(KhuyenMai khuyenMai) {
        if (timTheoTenKhuyenMai(khuyenMai.getTenKhuyenMai()) != null || timTheoMaKhuyenMai(khuyenMai.getMaKhuyenMai()) != null) {
            return null;
        }
        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public KhuyenMai updateKhuyenMai(KhuyenMai khuyenMai, Integer idKhuyenMai) {
        KhuyenMai khuyenmaitheoten = timTheoTenKhuyenMai(khuyenMai.getTenKhuyenMai());
        if (khuyenmaitheoten != null && !khuyenmaitheoten.getId().equals(idKhuyenMai)) {
            return null;
        }

        KhuyenMai khuyenMai1 = getKhuyenMaiById(idKhuyenMai);
        khuyenMai1.setMaKhuyenMai(khuyenMai.getMaKhuyenMai());
        khuyenMai1.setTenKhuyenMai(khuyenMai.getTenKhuyenMai());
        khuyenMai1.setKieuKhuyenMai(khuyenMai.getKieuKhuyenMai());
        khuyenMai1.setGiaTri(khuyenMai.getGiaTri());
        khuyenMai1.setMucGiamToiDa(khuyenMai.getMucGiamToiDa());
        khuyenMai1.setNgayBatDau(khuyenMai.getNgayBatDau());
        khuyenMai1.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        khuyenMai1.setTrangThai(khuyenMai.getTrangThai());
        return khuyenMaiRepository.save(khuyenMai1);

    }

    @Override
    public void deleteKhuyenMai(Integer idKhuyenMai) {
        KhuyenMai khuyenMai = getKhuyenMaiById(idKhuyenMai);
        khuyenMai.setTrangThai("Ngung hoat dong");
        khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public KhuyenMai timTheoTenKhuyenMai(String tenKhuyenMai) {
        Optional<KhuyenMai> khuyenMai = khuyenMaiRepository.timKiemTenKhuyenMai(tenKhuyenMai);
        if (khuyenMai.isPresent()) {
            return khuyenMai.get();
        } else {
            return null;
        }
    }

    @Override
    public KhuyenMai timTheoMaKhuyenMai(String maKhuyenMai) {
        Optional<KhuyenMai> khuyenMai1 = khuyenMaiRepository.timKiemMaKhuyenMai(maKhuyenMai);
        if (khuyenMai1.isPresent()) {
            return khuyenMai1.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<KhuyenMai> getPaginationKhuyenMai(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return khuyenMaiRepository.findAll(pageable);
    }

    @Override
    public Page<KhuyenMai> timKiemKhuyenMai(String maKhuyenMai, String tenKhuyenMai, String kieuKhuyenMai,
                                             Float mucGiamToiDa, String trangThai, Date ngayBatDau, Date ngayKetThuc, Pageable pageable) {
        return khuyenMaiRepository.findKhuyenMaiByCriteria(maKhuyenMai, tenKhuyenMai, kieuKhuyenMai, mucGiamToiDa, trangThai, ngayBatDau, ngayKetThuc, pageable);
    }

}
