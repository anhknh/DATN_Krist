package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.SanPham;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Override
    public ArrayList<ChiTietSanPham> getAllCTSP() {
        return (ArrayList<ChiTietSanPham>) chiTietSanPhamRepository.findAll();
    }

    @Override
    public ChiTietSanPham getCTSPById(int id) {
        return chiTietSanPhamRepository.findById(id).get();
    }

    @Override
    public ChiTietSanPham findFristProductDetail(SanPham sanPham) {
        return chiTietSanPhamRepository.findFirstBySanPham(sanPham);
    }
}
