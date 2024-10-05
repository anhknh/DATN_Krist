package com.example.kristp.service;

import com.example.kristp.entity.SanPham;

import java.util.ArrayList;
import java.util.List;

public interface TimKiemSanPhamService {
    ArrayList<SanPham> timKiemSanPham(List<String> tenSanPham,
                                      List<Integer> danhMucId,
                                      List<Integer> chatLieuId,
                                      List<Integer> tayAoId,
                                      List<Integer> coAoId,
                                      List<Integer> mauSacId,
                                      List<Integer> sizeId);
}
