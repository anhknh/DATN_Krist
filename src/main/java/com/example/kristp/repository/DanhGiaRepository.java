package com.example.kristp.repository;

import com.example.kristp.entity.DanhGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {

    @Query("SELECT d FROM DanhGia d WHERE d.hoaDonChiTiet.chiTietSanPham.sanPham.id = :idSanPham")
    Page<DanhGia> findBySanPhamId(@Param("idSanPham") Integer idSanPham, Pageable pageable);

    // Kiểm tra khách hàng đã đánh giá hóa đơn chi tiết hay chưa
    @Query("SELECT COUNT(d) > 0 FROM DanhGia d WHERE d.hoaDonChiTiet.id = :idHoaDonChiTiet")
    boolean existsByHoaDonChiTietId(@Param("idHoaDonChiTiet") Integer idHoaDonChiTiet);
}
