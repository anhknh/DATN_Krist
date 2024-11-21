package com.example.kristp.repository;

import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    GioHang findByKhachHangId(Integer khachHangId);
}


