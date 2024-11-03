package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;

import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    ChiTietSanPham findFirstBySanPham(SanPham sanPham);
    ChiTietSanPham findByQrCode(String qrCode);

        @Query("SELECT DISTINCT ms FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "JOIN ctp.mau ms " +
                "WHERE sp.id = :sanPhamId")
        List<MauSac> findDistinctColorsByProductId(@Param("sanPhamId") Integer sanPhamId);

        @Query("SELECT DISTINCT s FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "JOIN ctp.size s " +
                "WHERE sp.id = :sanPhamId")
        List<Size> findDistinctSizesByProductId(@Param("sanPhamId") Integer sanPhamId);

        @Query("SELECT ctp FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "JOIN ctp.mau ms " +
                "JOIN ctp.size s " +
                "WHERE sp.id = :sanPhamId AND ms.id = :mauSacId AND s.id = :sizeId")
        ChiTietSanPham findProductDetailByColorAndSize(@Param("sanPhamId") Integer sanPhamId,
                                                       @Param("mauSacId") Integer mauSacId,
                                                       @Param("sizeId") Integer sizeId);

        @Query("SELECT ctp FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "WHERE sp.id = :sanPhamId")
        List<ChiTietSanPham> findAllProductDetailsByProductId(@Param("sanPhamId") Integer sanPhamId);
        

}
