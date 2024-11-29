package com.example.kristp.service.impl;

import com.example.kristp.entity.DanhGia;
import com.example.kristp.repository.DanhGiaRepository;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.service.DanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DanhGiaServiceImpl implements DanhGiaService {

    @Autowired
    private DanhGiaRepository danhGiaRepository;
    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;

    @Override
    public Page<DanhGia> getDanhGiaBySanPham(Integer idSanPham, Integer limit) {
        return danhGiaRepository.findBySanPhamId(idSanPham, PageRequest.of(0, limit));
    }

    @Override
    public DanhGia getDanhGiaById(Integer id) {
        return null;
    }

    @Override
    public boolean checkDanhGiaAndDonHang(Integer id) {
        return danhGiaRepository.existsByHoaDonChiTietId(id);
    }

    @Override
    public boolean luuDanhGia(DanhGia danhGia) {
        // Kiểm tra khách hàng đã mua sản phẩm này chưa
        boolean daMuaSanPham = hoaDonChiTietRepo.existsByHoaDon_KhachHang_IdAndChiTietSanPham_Id(
                danhGia.getKhachHang().getId(), danhGia.getHoaDonChiTiet().getChiTietSanPham().getId());

        if (!daMuaSanPham) {
            return false;
        }

        // Kiểm tra khách hàng đã đánh giá sản phẩm này chưa
        boolean daDanhGia = danhGiaRepository.existsByHoaDonChiTietId(
                danhGia.getHoaDonChiTiet().getId());

        if (daDanhGia) {
            return false;
        }
        danhGiaRepository.save(danhGia);
        return true;
    }
}
