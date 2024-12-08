package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {

    @Query("select hct from HoaDonChiTiet hct where hct.hoaDon = :hoaDon")
    Page<HoaDonChiTiet> getHoaDonChiTietByHoaDon(HoaDon hoaDon, Pageable pageable);

    @Query("select hct from HoaDonChiTiet hct where hct.hoaDon = :hoaDon")
    List<HoaDonChiTiet> getHoaDonChiTietByHoaDonList(HoaDon hoaDon);

    @Query("SELECT hct FROM HoaDonChiTiet hct WHERE hct.hoaDon = :hoaDon AND hct.chiTietSanPham = :chiTietSanPham")
    HoaDonChiTiet findByHoaDonAndChiTietSanPham(@Param("hoaDon") HoaDon hoaDon, @Param("chiTietSanPham") ChiTietSanPham chiTietSanPham);

    boolean existsByHoaDon_KhachHang_IdAndChiTietSanPham_Id(Integer khachHangId, Integer chiTietSanPhamId);
    boolean existsByHoaDon_IdAndChiTietSanPham_Id(Integer idHoaDon, Integer chiTietSanPhamId);


}
