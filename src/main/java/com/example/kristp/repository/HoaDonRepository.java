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
    @Query(value = "select hd from HoaDon hd where hd.trangThai = ?1 and hd.")
    public Page<HoaDon> getPaginationTrangThai(Pageable pageable , HoaDonStatus trangThai);

//Thống kê
    @Query(nativeQuery = true , value = "SELECT \n" +
            "\tISNULL(SUM(hd.tong_tien + hd.phi_van_chuyen), 0) AS Tong_Tien\n" +
            "FROM \n" +
            "    master.dbo.spt_values\n" +
            "LEFT JOIN \n" +
            "    hoa_don hd ON FORMAT(hd.ngay_tao, 'MM-yyyy') = FORMAT(DATEFROMPARTS(YEAR(GETDATE()), spt_values.number, 1), 'MM-yyyy')\n" +
            "    AND hd.trang_thai_thanh_toan = 'DA_THANH_TOAN' AND hd.trang_thai = 'DA_THANH_TOAN' -- Lọc chỉ các hóa đơn đã thanh toán\n" +
            "WHERE \n" +
            "    spt_values.type = 'P' \n" +
            "    AND spt_values.number BETWEEN 1 AND 12 \n" +
            "GROUP BY \n" +
            "    FORMAT(DATEFROMPARTS(YEAR(GETDATE()), spt_values.number, 1), 'MM-yyyy')\n" +
            "ORDER BY \n" +
            "    FORMAT(DATEFROMPARTS(YEAR(GETDATE()), spt_values.number, 1), 'MM-yyyy');")
    List<String> thongKeTungThang();


    @Query("select hd from HoaDon hd order by hd.id desc ")
    Page<HoaDon> findTop5(Pageable pageable );


    @Query("select SUM(hd.tongTien) from HoaDon hd where cast(hd.ngaySua as DATE ) = current date AND  hd.trangThaiThanhToan = :trangthai")
    Double getDoanhThuHomNay(@Param("trangthai")HoaDonStatus status);

    @Query("SELECT COUNT(ctsp.id) " +
            "FROM HoaDon hd " +
            "JOIN HoaDonChiTiet hdct ON hd.id = hdct.hoaDon.id " +
            "JOIN ChiTietSanPham ctsp ON hdct.chiTietSanPham.id = ctsp.id " +
            "WHERE hd.trangThaiThanhToan = :trangthai " +
            "AND FUNCTION('MONTH', hd.ngaySua) = FUNCTION('MONTH', CURRENT_DATE)")
    Integer getSanPhamBanTrongThang(@Param("trangthai")HoaDonStatus status);

    @Query("select SUM(hd.tongTien) from HoaDon hd where FUNCTION('YEAR', hd.ngaySua) = FUNCTION('YEAR', CURRENT_DATE) AND  hd.trangThaiThanhToan = :trangthai")
    Double getDoanhThuTrongNam(@Param("trangthai")HoaDonStatus status);

    @Query(value = "SELECT \n" +
            "    COALESCE(COUNT(hd.trang_thai), 0) AS count\n" +
            "FROM \n" +
            "    (VALUES\n" +
            "        ('CHO_XAC_NHAN'),\n" +
            "        ('DANG_XU_LY'),\n" +
            "        ('DANG_GIAO_HANG'),\n" +
            "        ('HOAN_TAT'),\n" +
            "        ('HOA_DON_CHO'),\n" +
            "        ('DA_THANH_TOAN'),\n" +
            "        ('CHUA_THANH_TOAN')\n" +
            "    ) AS t(trang_thai)\n" +
            "LEFT JOIN hoa_don hd ON t.trang_thai = hd.trang_thai\n" +
            "GROUP BY t.trang_thai\n" +
            "ORDER BY t.trang_thai;" , nativeQuery = true)
     List<Integer> thongKeTrangThaiDonHang();
}
