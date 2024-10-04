package com.example.kristp.service;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;

import java.util.ArrayList;

public interface DanhMucService {
    ArrayList<DanhMuc> getAllDanhMuc();
    DanhMuc getDanhmucById(int id);
    DanhMuc addDanhMuc(DanhMuc danhMuc);

    DanhMuc updateDanhMuc(DanhMuc danhMuc , Integer idDanhMuc);

    void deleteDanhMuc(Integer idDanhMuc);

    DanhMuc timTheoTen(String tenDanhMuc);

}
