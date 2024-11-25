package com.example.kristp.service.impl;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
        KhachHang khachHang = new KhachHang();
        khachHang.setTaiKhoan(taiKhoan);
        khachHang.setTenKhachHang(tenKhachHang);
        khachHang.setSdtKh(sdtKh);
        khachHang.setTrangThai(Status.ACTIVE);
        return khachHangRepository.save(khachHang);
    }


    public void updateKhachHang(KhachHang kh) {
        Date ngaySua = new Date();
        kh.setNgaySua(ngaySua);
        khachHangRepository.save(kh);
    }
    @Override
    public List<KhachHang> getAllKhachHang() {

        return khachHangRepository.findAll();
    }

    @Override
    public void deleteKhachHang(Integer id) {
        khachHangRepository.deleteById(id);
    }


}
