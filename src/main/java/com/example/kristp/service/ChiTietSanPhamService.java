package com.example.kristp.service;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.Size;
import com.example.kristp.entity.dto.ChiTietSanPhamDto;
import com.example.kristp.entity.dto.ChiTietSanPhamDtoEdit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ChiTietSanPhamService {
    ArrayList<ChiTietSanPham> getAllCTSP();
    ChiTietSanPham getCTSPById(int id);
    ChiTietSanPham findFristProductDetail(SanPham sanPham);

    List<MauSac> getColorsByProductId(Integer productId);
    List<Size> getSizesByProductId(Integer productId);
    ChiTietSanPham getProductDetailByColorAndSize(Integer productId, Integer colorId, Integer sizeId);
    List<ChiTietSanPham> getProductDetailsByProductId(Integer productId);
// Hiện tại chưa được sử dụng
    List<ChiTietSanPham> getAllChiTietSanPhamSPHD();

    List<Integer> getSizeIdByProductIdAndColorId(Integer productId, Integer colorId);

    ChiTietSanPham getByColorAndSize(Integer colorId, Integer sizeId, Integer productId);

    boolean addChioTietSanPham (List<ChiTietSanPhamDto> chiTietSanPham, Integer idSanPham) throws IOException;
    boolean updateChiTietSanPham (ChiTietSanPhamDtoEdit chiTietSanPham, Integer idChiTiet, Integer idSanPham) throws IOException;

    Page<ChiTietSanPham> getPageChiTietSanPham(Integer idSanPham, Pageable pageable);
    boolean changeStatus(Integer idChiTiet);
}
