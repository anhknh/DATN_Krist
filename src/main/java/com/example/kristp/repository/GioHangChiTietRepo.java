package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
    List<GioHangChiTiet> findByGioHang_Id(Integer idGioHang);
   GioHangChiTiet findGioHangChiTietById(Integer idGioHangChiTiet);
    List<GioHangChiTiet> findByGioHangAndChiTietSanPham(GioHang gioHang, ChiTietSanPham chiTietSanPham);
    List<GioHangChiTiet> findByChiTietSanPhamAndGioHang_KhachHang(ChiTietSanPham chiTietSanPham, KhachHang khachHang);
}