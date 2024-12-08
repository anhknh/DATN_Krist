package com.example.kristp.service;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.SanPhamYeuThich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SanPhamYeuThichService {

    boolean addSanPhamYeuThich(Integer idSanPham);
    boolean deleteSanPhamYeuThich(Integer idSanPham);
    Page<SanPhamYeuThich> pageSanPhamYeuThich(KhachHang khachHang, Pageable pageable);
    boolean checkSanPhamYeuThich(Integer idSanPham);

}
