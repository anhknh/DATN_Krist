package com.example.kristp.service;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;

public interface KhuyenMaiService {
    ArrayList<KhuyenMai> getAllKhuyenMai();
    KhuyenMai getKhuyenMaiById(int id);
    KhuyenMai addKhuyenMai(KhuyenMai khuyenMai);
    void updateKhuyenMaiStatus();
    KhuyenMai updateKhuyenMai(KhuyenMai khuyenMai , Integer idKhuyenMai);

    void deleteKhuyenMai(Integer idKhuyenMai);

    KhuyenMai timTheoTenKhuyenMai(String tenKhuyenMai);

    KhuyenMai timTheoMaKhuyenMai(String maKhuyenMai);
    Page<KhuyenMai> getPaginationKhuyenMai(Integer pageNo);
    public Page<KhuyenMai> timKiemKhuyenMai(String maKhuyenMai, String tenKhuyenMai, Boolean kieuKhuyenMai,
                                            Float mucGiamToiDa,  String trangThai, Date ngayBatDau, Date ngayKetThuc, Pageable pageable);
}
