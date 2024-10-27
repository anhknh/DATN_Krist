package com.example.kristp.service;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface DanhMucService {
    ArrayList<DanhMuc> getAllDanhMuc();
    ArrayList<DanhMuc> getAllDanhMucHD();
    DanhMuc getDanhmucById(int id);
    DanhMuc addDanhMuc(DanhMuc danhMuc);

    DanhMuc updateDanhMuc(DanhMuc danhMuc , Integer idDanhMuc);

    void deleteDanhMuc(Integer idDanhMuc);

    DanhMuc timTheoTen(String tenDanhMuc);

    Page<DanhMuc> getPaginationDanhMuc(Integer pageNo);
    Page<DanhMuc> timTatCaTheoTen(Integer pageNo,String ten);
}
