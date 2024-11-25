package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;

import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.Size;
import org.springframework.data.domain.Pageable;
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
        
//Lấy các chi tiết sản phẩm san pham có trạng thái là đang hoạt động
        @Query("from ChiTietSanPham ctsp join SanPham sp on ctsp.sanPham.id = sp.id where sp.trangThai = 1")
    List<ChiTietSanPham> findAllProductDetailsSPHD();

        @Query(value = "select id_size from chi_tiet_san_pham where id_san_pham = :id and id_mau_sac = :idMau", nativeQuery = true)
        List<Integer> getSizeByIdProductAndIdColor(Integer id, Integer idMau);

        ChiTietSanPham findByMau_IdAndSize_IdAndSanPham_Id(Integer mau_id, Integer size_id, Integer sanPham_id);

}
