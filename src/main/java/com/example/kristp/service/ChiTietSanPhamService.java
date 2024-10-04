package com.example.kristp.service;

import com.example.kristp.entity.ChiTietSanPham;


import java.util.ArrayList;

public interface ChiTietSanPhamService {
    ArrayList<ChiTietSanPham> getAllCTSP();
    ChiTietSanPham getCTSPById(int id);
}
