package com.example.kristp.repository;

import com.example.kristp.entity.SanPham;
import com.example.kristp.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

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
}
