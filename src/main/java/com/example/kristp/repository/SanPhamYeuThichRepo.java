package com.example.kristp.repository;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.SanPhamYeuThich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamYeuThichRepo extends JpaRepository<SanPhamYeuThich, Integer> {

    Page<SanPhamYeuThich> findSanPhamYeuThichByKhachHang(KhachHang khachHang, Pageable pageable);

    boolean existsByKhachHangAndSanPham_Id(KhachHang khachHang, Integer idSanPham);

    SanPhamYeuThich findBySanPham_IdAndKhachHang(Integer idSanPham, KhachHang khachHang);

}
