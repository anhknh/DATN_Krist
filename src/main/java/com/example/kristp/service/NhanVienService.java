package com.example.kristp.service;

import com.example.kristp.entity.NhanVien;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface NhanVienService {
    ArrayList<NhanVien> getAllNhanVien();
    ArrayList<NhanVien> getAllNhanVienHD();

    NhanVien getNhanVienById(int id);

    NhanVien addNhanVien(NhanVien nhanVien);

    NhanVien updateNhanVien(NhanVien nhanVien , Integer idNhanVien);

    void deleteNhanVien(Integer idNhanVien);

    NhanVien timTheoMaNhanVien(String maNhanVien);
    Page<NhanVien> getPaginationNhanVien(Integer pageNo);
    Page<NhanVien> timTatCaTheoMa(Integer pageNo, String ma);
}
