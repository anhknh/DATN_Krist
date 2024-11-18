package com.example.kristp.service;


import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.DiaChi;
import com.example.kristp.entity.KhachHang;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface DiaChiService {
    Page<DiaChi> getAllActiveDiaChi(Pageable pageable);
    List<DiaChi> getAllActiveDiaChiByUser(KhachHang khachHang);
    DiaChi getDiaChiById(Integer id);
    DiaChi addDiaChi(DiaChi diaChi);
    DiaChi updateDiaChi(DiaChi diaChi, Integer idDiaChi);
    void deleteDiaChi(Integer id);

}
