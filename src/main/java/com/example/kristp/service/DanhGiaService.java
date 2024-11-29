package com.example.kristp.service;

import com.example.kristp.entity.DanhGia;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DanhGiaService {

    Page<DanhGia> getDanhGiaBySanPham(Integer idSanPham, Integer limit);
    DanhGia getDanhGiaById(Integer id);
    boolean checkDanhGiaAndDonHang(Integer idHoaDonChiTiet);

    boolean luuDanhGia(DanhGia danhGia);
}
