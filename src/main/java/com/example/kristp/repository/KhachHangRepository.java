package com.example.kristp.repository;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByTaiKhoan(TaiKhoan id);
}
