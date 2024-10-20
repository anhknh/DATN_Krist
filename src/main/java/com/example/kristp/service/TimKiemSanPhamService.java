package com.example.kristp.service;

import com.example.kristp.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface TimKiemSanPhamService {
    Page<SanPham> timKiemSanPham(String tenSanPham,
                                 List<Integer> danhMucId,
                                 List<Integer> chatLieuId,
                                 List<Integer> tayAoId,
                                 List<Integer> coAoId,
                                 List<Integer> mauSacId,
                                 List<Integer> sizeId,
                                 Pageable pageable);
}
