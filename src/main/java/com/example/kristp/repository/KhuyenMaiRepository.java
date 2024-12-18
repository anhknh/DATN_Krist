package com.example.kristp.repository;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.MauSac;
import com.example.kristp.enums.Status;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhuyenMaiRepository  extends JpaRepository<KhuyenMai, Integer> {


    List<KhuyenMai> findByTrangThai(String trangThai);


    @Query("SELECT km FROM KhuyenMai km WHERE km.tenKhuyenMai = :tenKhuyenMai")
    Optional<KhuyenMai> timKiemTenKhuyenMai(@Param("tenKhuyenMai") String tenKhuyenMai);

    @Query("SELECT km FROM KhuyenMai km WHERE km.maKhuyenMai = :maKhuyenMai")
    Optional<KhuyenMai> timKiemMaKhuyenMai(@Param("maKhuyenMai") String maKhuyenMai);

    @Query("SELECT km FROM KhuyenMai km WHERE "
            + "(:maKhuyenMai IS NULL OR km.maKhuyenMai LIKE %:maKhuyenMai%) AND "
            + "(:tenKhuyenMai IS NULL OR km.tenKhuyenMai LIKE %:tenKhuyenMai%) AND "
            + "(:kieuKhuyenMai IS NULL OR km.kieuKhuyenMai = :kieuKhuyenMai) AND "
            + "(:mucGiamToiDa IS NULL OR km.mucGiamToiDa <= :mucGiamToiDa) AND "
            + "(:trangThai IS NULL OR km.trangThai LIKE %:trangThai%) AND "
            + "(:ngayBatDau IS NULL OR km.ngayBatDau >= :ngayBatDau) AND "
            + "(:ngayKetThuc IS NULL OR km.ngayKetThuc <= :ngayKetThuc) "
            + "ORDER BY km.tenKhuyenMai")
    Page<KhuyenMai> findKhuyenMaiByCriteria(
            @Param("maKhuyenMai") String maKhuyenMai,
            @Param("tenKhuyenMai") String tenKhuyenMai,
            @Param("kieuKhuyenMai") Boolean kieuKhuyenMai,
            @Param("mucGiamToiDa") Float mucGiamToiDa,
            @Param("trangThai") String trangThai,
            @Param("ngayBatDau") Date ngayBatDau,
            @Param("ngayKetThuc") Date ngayKetThuc,
            Pageable pageable
    );
}
