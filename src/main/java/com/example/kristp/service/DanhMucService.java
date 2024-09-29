package com.example.kristp.service;

import com.example.kristp.entity.DanhMuc;

import java.util.ArrayList;

public interface DanhMucService {
    ArrayList<DanhMuc> getAllDanhMuc();
    DanhMuc getDanhmucById(int id);
}
