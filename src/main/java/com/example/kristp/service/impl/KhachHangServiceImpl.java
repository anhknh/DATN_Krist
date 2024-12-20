package com.example.kristp.service.impl;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    KhachHangRepository khachHangRepository;


    @Override
    public KhachHang getKHByUserId(TaiKhoan id) {
        return khachHangRepository.findByTaiKhoan(id);
    }

    @Override
    public KhachHang saveKhachHang(String tenKhachHang, TaiKhoan taiKhoan, String sdtKh) {
        if(khachHangRepository.existsBySoDienThoai(sdtKh)) {
            return null;
        }
        KhachHang khachHang = new KhachHang();
        khachHang.setTaiKhoan(taiKhoan);
        khachHang.setTenKhachHang(tenKhachHang);
        khachHang.setSoDienThoai(sdtKh);
        khachHang.setTrangThai(Status.ACTIVE);
        return khachHangRepository.save(khachHang);
    }

    @Override
    public ArrayList<KhachHang> getAllKhachHang() {
        return (ArrayList<KhachHang>) khachHangRepository.findAll();
    }


    public void updateKhachHang(KhachHang kh) {
        Date ngaySua = new Date();
        kh.setNgaySua(ngaySua);
        khachHangRepository.save(kh);
    }

    @Override
    public KhachHang getKhachHangById(int id) {
        return khachHangRepository.findById(id).get();
    }

    @Override
    public KhachHang addKhachHang(KhachHang khachHang) {
        khachHang.setTrangThai(Status.ACTIVE);
        return khachHangRepository.save(khachHang);
    }

    @Override
    public KhachHang updateKhachHang(KhachHang khachHang, Integer idKhachHang) {
        KhachHang tayao1 = getKhachHangById(idKhachHang);
        tayao1.setTenKhachHang(khachHang.getTenKhachHang());
        tayao1.setSoDienThoai(khachHang.getSoDienThoai());
        return khachHangRepository.save(tayao1);
    }



    @Override
    public void deleteKhachHang(Integer idKhachHang) {
        KhachHang khachHang = getKhachHangById(idKhachHang);
        TaiKhoan taiKhoan = khachHang.getTaiKhoan();
        // Chuyển trạng thái giữa ACTIVE và INACTIVE
        if (khachHang.getTrangThai() == Status.INACTIVE) {
            khachHang.setTrangThai(Status.ACTIVE);
            taiKhoan.setTrangThai(Status.ACTIVE);
        } else {
            khachHang.setTrangThai(Status.INACTIVE);
            taiKhoan.setTrangThai(Status.INACTIVE);
        }
        khachHangRepository.save(khachHang);
    }

    @Override
    public Page<KhachHang> getPaginationKhachHang(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return khachHangRepository.findAll(pageable);
    }

    @Override
    public Page<KhachHang> timTatCaTheoTen(Integer pageNo, String tenKhachHang, String sdtKh) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return khachHangRepository.timKiemKhachHang(pageable,tenKhachHang,sdtKh);
    }

    @Override
    public KhachHang findBySoDienThoai(String soDienThoai) {
        Optional<KhachHang> khachHangOpt = khachHangRepository.findBySoDienThoai(soDienThoai);
        return khachHangOpt.orElse(null); // Trả về null nếu không tìm thấy khách hàng
    }

    @Override
    public boolean isSoDienThoaiExists(String soDienThoai) {
        return khachHangRepository.findBySoDienThoai(soDienThoai).isPresent();
    }


}
