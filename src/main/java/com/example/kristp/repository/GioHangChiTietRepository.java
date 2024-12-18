package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer> {
    // Truy vấn tuỳ chỉnh để tìm chi tiết giỏ hàng theo ID giỏ hàng
    List<GioHangChiTiet> findByGioHang_Id(Integer gioHangId);
    GioHangChiTiet findByGioHangAndChiTietSanPham(GioHang gioHang, ChiTietSanPham chiTietSanPham);

    Integer countGioHangChiTietByGioHang(GioHang gioHang);

    @Query("SELECT SUM(g.soLuong) FROM GioHangChiTiet g WHERE g.gioHang = :gioHang")
    Integer sumSoLuong(GioHang gioHang);

}
