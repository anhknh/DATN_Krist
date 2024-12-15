package com.example.kristp.service;

import com.example.kristp.entity.NhanVien;
import com.example.kristp.entity.dto.NhanVienDto;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface NhanVienService {
    ArrayList<NhanVien> getAllNhanVien();
    ArrayList<NhanVien> getAllNhanVienHD();

    NhanVien getNhanVienById(int id);

    NhanVien addNhanVien(NhanVienDto nhanVien);

    NhanVien updateNhanVien(NhanVienDto nhanVien , Integer idNhanVien);

    void deleteNhanVien(Integer idNhanVien);

    NhanVien timTheoMaNhanVien(String maNhanVien);
    Page<NhanVien> getPaginationNhanVien(Integer pageNo);
    Page<NhanVien> timTatCaTheoMa(Integer pageNo, String ma);

    NhanVienDto fetchNhanVien(String maNhanVien);
}
