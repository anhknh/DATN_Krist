package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;

import com.example.kristp.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    ChiTietSanPham findFirstBySanPham(SanPham sanPham);
}
