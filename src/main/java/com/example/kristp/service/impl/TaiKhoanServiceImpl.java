package com.example.kristp.service.impl;

import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    TaiKhoanRepository taiKhoanRepository;


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
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhapAndMatKhau(tenDangNhap, matKhau);

        if (taiKhoan != null && matKhau.matches(taiKhoan.getMatKhau())) {
            return taiKhoan;
        } else {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
    }


}
