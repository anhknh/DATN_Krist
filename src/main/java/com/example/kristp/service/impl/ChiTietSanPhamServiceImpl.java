package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MauSac> getColorsByProductId(Integer productId) {
        return chiTietSanPhamRepository.findDistinctColorsByProductId(productId);
    }

    @Override
    public List<Size> getSizesByProductId(Integer productId) {
        return chiTietSanPhamRepository.findDistinctSizesByProductId(productId);
    }

    @Override
    public ChiTietSanPham getProductDetailByColorAndSize(Integer productId, Integer colorId, Integer sizeId) {
        return chiTietSanPhamRepository.findProductDetailByColorAndSize(productId, colorId, sizeId);
    }

    @Override
    public List<ChiTietSanPham> getProductDetailsByProductId(Integer productId) {
        return chiTietSanPhamRepository.findAllProductDetailsByProductId(productId);
    }
}
