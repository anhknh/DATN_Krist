package com.example.kristp.repository;

import com.example.kristp.entity.ChiTietSanPham;

import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.Size;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;
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
                "WHERE sp.id = :sanPhamId and ctp.trangThai = 1")
        List<MauSac> findDistinctColorsByProductId(@Param("sanPhamId") Integer sanPhamId);

        @Query("SELECT DISTINCT s FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "JOIN ctp.size s " +
                "WHERE sp.id = :sanPhamId and ctp.trangThai = 1")
        List<Size> findDistinctSizesByProductId(@Param("sanPhamId") Integer sanPhamId);

        @Query("SELECT ctp FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "JOIN ctp.mau ms " +
                "JOIN ctp.size s " +
                "WHERE sp.id = :sanPhamId AND ms.id = :mauSacId AND s.id = :sizeId and ctp.trangThai = 1")
        ChiTietSanPham findProductDetailByColorAndSize(@Param("sanPhamId") Integer sanPhamId,
                                                       @Param("mauSacId") Integer mauSacId,
                                                       @Param("sizeId") Integer sizeId);

        @Query("SELECT ctp FROM ChiTietSanPham ctp " +
                "JOIN ctp.sanPham sp " +
                "WHERE sp.id = :sanPhamId and ctp.trangThai = 1")
        List<ChiTietSanPham> findAllProductDetailsByProductId(@Param("sanPhamId") Integer sanPhamId);
        
//Lấy các chi tiết sản phẩm san pham có trạng thái là đang hoạt động
        @Query("from ChiTietSanPham ctsp join SanPham sp on ctsp.sanPham.id = sp.id where ctsp.trangThai = 1")
    List<ChiTietSanPham> findAllProductDetailsSPHD();

        @Query(value = "select id_size from chi_tiet_san_pham where id_san_pham = :id and id_mau_sac = :idMau and trang_thai = 1", nativeQuery = true)
        List<Integer> getSizeByIdProductAndIdColor(Integer id, Integer idMau);

        ChiTietSanPham findByMau_IdAndSize_IdAndSanPham_Id(Integer mau_id, Integer size_id, Integer sanPham_id);

        @Query("select  ct from ChiTietSanPham  ct where ct.sanPham.id = :idSanPham")
        Page<ChiTietSanPham> findChiTietBySanPham(Integer idSanPham, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(ct) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ChiTietSanPham ct " +
            "WHERE ct.mau.id = :mauId " +
            "AND ct.size.id = :sizeId " +
            "AND ct.sanPham.id = :sanPhamId " +
            "AND ct.qrCode = :qrCode " +
            "AND (:id IS NULL OR ct.id != :id)")
    boolean existsBySanPhamAndMauAndSizeAndQrCodeExcludeItself(
            @Param("sanPhamId") Integer sanPhamId,
            @Param("mauId") Integer mauId,
            @Param("sizeId") Integer sizeId,
            @Param("qrCode") String qrCode,
            @Param("id") Integer id);


    Page<ChiTietSanPham> findAllByTrangThai(Status trangThai, Pageable pageable);

}
