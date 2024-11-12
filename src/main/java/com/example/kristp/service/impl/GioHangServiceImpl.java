package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.GioHangChiTietRepo;
import com.example.kristp.repository.GioHangRepository;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.GioHangChiTietService;
import com.example.kristp.service.GioHangService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;


    @Override
    public GioHang findGioHangByKhachHangId(KhachHang khachHang) {
        return gioHangRepository.findByKhachHangId(khachHang.getId());
    }

    @Override
    public void addSanPhamToGioHang(GioHang gioHang) {
        // Kiểm tra nếu giỏ hàng đã có, nếu chưa thì lưu giỏ hàng mới
        if (gioHang.getId() == null) {
            gioHangRepository.save(gioHang);
        } else {
            gioHangRepository.save(gioHang); // Cập nhật giỏ hàng nếu đã tồn tại
        }
    }

    @Override
    public void deleteGioHangById(List<Integer> id) {
        gioHangRepository.deleteAllById(id);
    }
}
