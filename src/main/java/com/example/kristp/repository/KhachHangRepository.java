package com.example.kristp.repository;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    KhachHang findByTaiKhoan(TaiKhoan taiKhoan);

    Optional<KhachHang> findBySoDienThoai(String soDienThoai);
    @Query("SELECT kh FROM KhachHang kh " +
            "WHERE (:tenKhachHang IS NULL OR kh.tenKhachHang LIKE %:tenKhachHang%) " +
            "AND (:sdtKh IS NULL OR kh.soDienThoai LIKE %:sdtKh%)")
    Page<KhachHang> timKiemKhachHang(Pageable pageable,
                                     @Param("tenKhachHang") String tenKhachHang,
                                     @Param("sdtKh") String sdtKh);

    @Query(value = "select kh from KhachHang kh where kh.tenKhachHang LIKE :ten")
    Page<KhachHang> findAllByTenLike(Pageable pageable, @Param("ten") String ten);

    @Query("from KhachHang kh where kh.trangThai = 1")
    List<KhachHang> findAllKhachHang();

    @Query("SELECT COUNT(k) > 0 FROM KhachHang k WHERE k.soDienThoai = :soDienThoai")
    boolean existsBySoDienThoai(@Param("soDienThoai") String soDienThoai);

}

