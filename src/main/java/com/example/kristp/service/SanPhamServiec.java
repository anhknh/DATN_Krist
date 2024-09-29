package com.example.kristp.service;

import com.example.kristp.entity.SanPham;
import com.example.kristp.enums.Status;

import java.util.ArrayList;

public interface SanPhamServiec {
    ArrayList<SanPham> findSanphamTop4ngaytaoDesc();
    ArrayList<SanPham> findAllSanPham();
    SanPham findSanphamById(Integer Id);
    Integer addSanpham(Integer danhMuc, Integer chatLieu, String tenSanPham, String moTa, Status trangThai);
    SanPham add(SanPham sanPham);
    SanPham update(SanPham sanPham, Integer id);
    SanPham delete(Integer id);
    boolean isTenExists(String tenSanPham);
}
