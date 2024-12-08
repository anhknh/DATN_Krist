package com.example.kristp.service.impl;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.SanPham;
import com.example.kristp.entity.SanPhamYeuThich;
import com.example.kristp.repository.SanPhamYeuThichRepo;
import com.example.kristp.service.SanPhamService;
import com.example.kristp.service.SanPhamYeuThichService;
import com.example.kristp.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SanPhamYeuThichServiceImpl implements SanPhamYeuThichService {

    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    SanPhamYeuThichRepo sanPhamYeuThichRepo;

    @Override
    public boolean addSanPhamYeuThich(Integer idSanPham) {
        SanPham sanPham = sanPhamService.findSanphamById(idSanPham);
        if(sanPham != null) {
            SanPhamYeuThich sanPhamYeuThich = new SanPhamYeuThich();
            sanPhamYeuThich.setSanPham(sanPham);
            sanPhamYeuThich.setKhachHang(Authen.khachHang);
            sanPhamYeuThichRepo.save(sanPhamYeuThich);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSanPhamYeuThich(Integer idSanPham) {
        SanPhamYeuThich sanPhamYeuThich1 = sanPhamYeuThichRepo.findBySanPham_IdAndKhachHang(idSanPham, Authen.khachHang);
        if(sanPhamYeuThich1 != null) {
            sanPhamYeuThichRepo.delete(sanPhamYeuThich1);
            return true;
        }
        return false;
    }

    @Override
    public Page<SanPhamYeuThich> pageSanPhamYeuThich(KhachHang khachHang, Pageable pageable) {
        return sanPhamYeuThichRepo.findSanPhamYeuThichByKhachHang(khachHang, pageable);
    }

    @Override
    public boolean checkSanPhamYeuThich(Integer idSanPham) {
        return sanPhamYeuThichRepo.existsByKhachHangAndSanPham_Id(Authen.khachHang, idSanPham);
    }
}
