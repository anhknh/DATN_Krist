package com.example.kristp.repository;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.SanPham;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
//    ArrayList<SanPham> findFirst5ByOrderByNgayTaoDesc();
//    SanPham findSanPhamById(Integer Id);
//
//    @Query(value = "DECLARE @id INT; " +
//            "INSERT INTO  [dbo].[san_pham] ([id_danh_muc], [id_chat_lieu], [ten_san_pham], [mo_ta],[ngay_tao], [ngay_sua], [trang_thai]) " +
//            "VALUES (:id_danh_muc, :id_chat_lieu, :ten_san_pham, :mo_ta, GETDATE(), GETDATE(), :trang_thai)\n" +
//            "SET @id = SCOPE_IDENTITY(); " +
//            "SELECT @id", nativeQuery = true)
//    Integer createSanPham(@Param("id_danh_muc") Integer danhMuc,
//                                  @Param("id_chat_lieu") Integer chatLieu ,
//                                  @Param("ten_san_pham") String tenSanPham,
//                                  @Param("mo_ta" ) String moTa,
//                                  @Param("trang_thai" ) Status trangThai);
//
//    boolean existsByTen(String tenSanPham);



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query("SELECT sp FROM SanPham sp " +
            "JOIN ChiTietSanPham cts ON sp.id = cts.sanPham.id " +
            "WHERE (:tenSanPham IS NULL OR sp.tenSanPham LIKE %:tenSanPham%) " +
            "AND (:danhMucId IS NULL OR sp.danhMuc.id IN :danhMucId) " +
            "AND (:chatLieuId IS NULL OR sp.chatLieu.id IN :chatLieuId) " +
            "AND (:tayAoId IS NULL OR cts.tayAo.id IN :tayAoId) " +
            "AND (:coAoId IS NULL OR cts.coAo.id IN :coAoId) " +
            "AND (:mauSacId IS NULL OR cts.mau.id IN :mauSacId) " +
            "AND (:sizeId IS NULL OR cts.size.id IN :sizeId)" +
            " AND sp.trangThai = 1")
    Page<SanPham> timKiemSanPham(@Param("tenSanPham") String tenSanPham,
                                 @Param("danhMucId") List<Integer> danhMucId,
                                 @Param("chatLieuId") List<Integer> chatLieuId,
                                 @Param("tayAoId") List<Integer> tayAoId,
                                 @Param("coAoId") List<Integer> coAoId,
                                 @Param("mauSacId") List<Integer> mauSacId,
                                 @Param("sizeId") List<Integer> sizeId,
                                 Pageable pageable);

    @Query(value = "SELECT DISTINCT TOP 4 sp.* " +
            "FROM san_pham sp " +
            "JOIN chi_tiet_san_pham ctsp ON sp.id = ctsp.id_san_pham " +
            " WHERE sp.trang_thai = 1" +
            "ORDER BY sp.ngay_tao DESC; ", nativeQuery = true)
    List<SanPham> finTop4SanPhamMoi();

    boolean existsByTenSanPhamAndTrangThai(String tenSanPham, Status trangThai);

    boolean existsByTenSanPham(String tenSanPham);


    @Query(value = "select sp from SanPham sp where size(sp.chiTietSanPham) > 0 ")
    Page<SanPham> findSanPhamsWithChiTietSanPham(Pageable pageable);


    @Query(value = "select sp from SanPham sp where sp.tenSanPham = :tenSanPham")
    Optional<SanPham> timKiemTenSanPham(@Param("tenSanPham") String tenSanPham);

    @Query(value = "select sp from SanPham sp where sp.tenSanPham LIKE :ten")
    Page<SanPham> findAllByTenLike(Pageable pageable, @Param("ten") String ten);

    @Query(value = "select sp from SanPham sp order by sp.ngayTao desc")
    Page<SanPham> findAllSanPham(Pageable pageable);

}
