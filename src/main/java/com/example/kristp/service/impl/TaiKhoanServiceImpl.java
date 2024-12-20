package com.example.kristp.service.impl;

import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.GioHangRepository;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.GioHangService;
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
    @Autowired
    GioHangRepository gioHangRepository;
    @Autowired
    KhachHangRepository khachHangRepository;


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
        return taiKhoanRepository.findByTenDangNhapAndMatKhauAndTrangThai(tenDangNhap, matKhau, Status.ACTIVE);
    }


    @Override
    public TaiKhoan taoTaiKhoan(TaiKhoan taiKhoan, String tenKhachHang, String sdtKh) {

        if(taiKhoanRepository.existsByTenDangNhapOrEmail(taiKhoan.getTenDangNhap(), taiKhoan.getEmail())){
            return null;
        }
        if(khachHangRepository.existsBySoDienThoai(sdtKh)) {
            return null;
        }
        taiKhoan.setChucVu("user");
        taiKhoan.setTrangThai(Status.ACTIVE);
        TaiKhoan taiKhoanNew = taiKhoanRepository.save(taiKhoan);
        KhachHang khachHangSaved = khachHangService.saveKhachHang(tenKhachHang, taiKhoanNew, sdtKh);

        GioHang gioHang = new GioHang();
        gioHang.setKhachHang(khachHangSaved);
        gioHang.setTrangThai(Status.ACTIVE);
        gioHangRepository.save(gioHang);
        return taiKhoanNew;
    }

    @Override
    public TaiKhoan taoTaiKhoanAdmin(TaiKhoan taiKhoan) {
        if(taiKhoanRepository.existsByTenDangNhapOrEmail(taiKhoan.getTenDangNhap(), taiKhoan.getEmail())){
            return null;
        }
        taiKhoan.setChucVu("staff");
        taiKhoan.setTrangThai(Status.ACTIVE);
        return taiKhoanRepository.save(taiKhoan);
    }
}
