package com.example.kristp.repository;

import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan,Integer> {

    TaiKhoan findByTenDangNhap(String tenDangNhap);

    TaiKhoan findByTenDangNhapAndMatKhauAndTrangThai(String tenDangNhap, String matKhau, Status trangThai);

    @Query(value = "DECLARE @id INT; " +
            "INSERT INTO  [dbo].[tai_khoan] ([ten_dang_nhap], [mat_khau], [email], [trang_thai], [chuc_vu], [ngay_tao], [ngay_sua]) " +
            "VALUES (:ten_dang_nhap, :mat_khau, :email, :trangThai, 'user', GETDATE(), GETDATE())\n" +
            "SET @id = SCOPE_IDENTITY(); " +
            "SELECT @id", nativeQuery = true)
    Integer taoTaiKhoan(@Param("ten_dang_nhap") String tenDangNhap,
                               @Param("mat_khau") String matKhau,
                               @Param("email") String email);


    @Query("SELECT COUNT(t) > 0 FROM TaiKhoan t WHERE t.tenDangNhap = :tenDangNhap OR t.email = :email")
    boolean existsByTenDangNhapOrEmail(@Param("tenDangNhap") String tenDangNhap, @Param("email") String email);


}
