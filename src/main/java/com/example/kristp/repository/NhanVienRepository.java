package com.example.kristp.repository;

import com.example.kristp.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query(value = "SELECT nv FROM NhanVien nv where nv.maNhanVien = :maNhanVien")
    Optional<NhanVien> timKiemMaNhanVien(@Param("maNhanVien") String maNhanVien);

    @Query(value = "select nv from NhanVien nv where nv.maNhanVien LIKE :ma")
    Page<NhanVien> findAllByMaLike(Pageable pageable, @Param("ma") String ma);

    @Query("from NhanVien n where n.trangThai = 1 ")
    List<NhanVien> findAllNhanVien();
}
