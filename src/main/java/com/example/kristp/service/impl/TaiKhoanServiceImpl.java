package com.example.kristp.service.impl;

import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    TaiKhoanRepository taiKhoanRepository;
    @Autowired
    KhachHangService khachHangService;


    @Override
    public ArrayList<TaiKhoan> getTaiKhoan() {
        return (ArrayList<TaiKhoan>) taiKhoanRepository.findAll();
    }

    @Override
    public TaiKhoan getTenDangNhap(String tenDangNhap) {
        return taiKhoanRepository.findByTenDangNhap(tenDangNhap);
    }

    @Override
    public TaiKhoan getUserId(Integer id) {
        return taiKhoanRepository.findById(id).orElse(null);
    }

    @Override
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        return taiKhoanRepository.findByTenDangNhapAndMatKhau(tenDangNhap, matKhau);
    }

    @Override
    public TaiKhoan taoTaiKhoan(TaiKhoan taiKhoan, String tenKhachHang) {
        taiKhoan.setChucVu("KhachHang");
        taiKhoan.setTrangThai(Status.ACTIVE);
        TaiKhoan taiKhoanNew = taiKhoanRepository.save(taiKhoan);
        khachHangService.saveKhachHang(tenKhachHang, taiKhoanNew);
        return taiKhoanNew;
    }
}
