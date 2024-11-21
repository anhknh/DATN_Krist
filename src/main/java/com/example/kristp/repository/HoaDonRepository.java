package com.example.kristp.repository;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT h FROM HoaDon h ORDER BY h.ngayDatHang DESC")
    List<HoaDon> findAllHoaDonsOrderByNgayDatHang();
    List<HoaDon> findByTrangThai(HoaDonStatus trangThai);

    Integer countByTrangThai(HoaDonStatus trangThai);


    @Query("SELECT h FROM HoaDon h WHERE h.trangThai = :trangThai ORDER BY h.ngayDatHang DESC")
    List<HoaDon> findByTrangThaiDonHang(@Param("trangThai") HoaDonStatus trangThai);

    // tìm kiếm đơn hàng
    @Query("SELECT h FROM HoaDon h WHERE " +
            "(:id IS NULL OR h.id = :id) AND " +
            "(h.ngayDatHang BETWEEN :ngayBatDau AND :ngayKetThuc)")
    List<HoaDon> findByFilters(
            @Param("id") Integer id,
            @Param("ngayBatDau") LocalDateTime ngayBatDau,
            @Param("ngayKetThuc") LocalDateTime ngayKetThuc
    );

      //count
        @Query("SELECT h.trangThai, COUNT(h) FROM HoaDon h GROUP BY h.trangThai")
        List<Object[]> countByTrangThaiGrouped();
    @Query(value = "select hd from HoaDon hd where hd.trangThai = ?1")
    public Page<HoaDon> getPaginationTrangThai(Pageable pageable , HoaDonStatus trangThai);
}
