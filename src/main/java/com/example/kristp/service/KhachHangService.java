package com.example.kristp.service;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface KhachHangService {
    KhachHang getKHByUserId(TaiKhoan id);
    KhachHang saveKhachHang(String tenKhachHang, TaiKhoan taiKhoan);
    ArrayList<KhachHang> getAllKhachHang();
    void updateKhachHang(KhachHang kh);
    KhachHang getKhachHangById(int id);
    KhachHang addKhachHang(KhachHang khachHang);

    KhachHang updateKhachHang(KhachHang khachHang , Integer idKhachHang);

    void deleteKhachHang(Integer idKhachHang);
    Page<KhachHang> getPaginationKhachHang(Integer pageNo);

    Page<KhachHang> timTatCaTheoTen(Integer pageNo, String tenKhachHang, String sdtKh);

}
