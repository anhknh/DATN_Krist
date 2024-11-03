package com.example.kristp.service;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface SanPhamService {
    ArrayList<SanPham> findSanphamTop4ngaytaoDesc();
    ArrayList<SanPham> findAllSanPham();
    SanPham findSanphamById(Integer Id);
    SanPham addSanPham(SanPham sanPham);

    SanPham updateSanPham(SanPham sanPham , Integer idSanPham);

    void deleteSanPham(Integer idSanPham);
    boolean isTenExists(String tenSanPham);
    SanPham timTheoTenSanPham(String tenSanPham);

    Page<SanPham> getPaginationSanPham(Integer pageNo);

    Page<SanPham> timTatCaTheoTen(Integer pageNo, String ten);

}
