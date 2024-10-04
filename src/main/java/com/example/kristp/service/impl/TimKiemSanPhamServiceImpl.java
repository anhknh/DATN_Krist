package com.example.kristp.service.impl;

import com.example.kristp.entity.SanPham;
import com.example.kristp.repository.SanPhamRepository;
import com.example.kristp.service.TimKiemSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimKiemSanPhamServiceImpl implements TimKiemSanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;


    @Override
    public ArrayList<SanPham> timKiemSanPham(List<String> tenSanPham,
                                             List<Integer> danhMucId,
                                             List<Integer> chatLieuId,
                                             List<Integer> tayAoId,
                                             List<Integer> coAoId,
                                             List<Integer> mauSacId,
                                             List<Integer> sizeId) {
        return (ArrayList<SanPham>) sanPhamRepository.timKiemSanPham(tenSanPham, danhMucId, chatLieuId, tayAoId, coAoId, mauSacId, sizeId);
    }
}
