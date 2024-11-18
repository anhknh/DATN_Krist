package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.repository.GioHangChiTietRepository;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.repository.GioHangChiTietRepo;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.repository.GioHangChiTietRepo;
import com.example.kristp.repository.GioHangChiTietRepository;
import com.example.kristp.service.GioHangChiTietService;
import com.example.kristp.service.GioHangService;
import com.example.kristp.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public ArrayList<GioHangChiTiet> getAllGioHangChiTiet(Integer idGioHang) {
        return (ArrayList<GioHangChiTiet>) gioHangChiTietRepo.findByGioHang_Id(idGioHang);

    }


    @Override
    public void saveCartItem(GioHangChiTiet gioHangChiTiet) {
        gioHangChiTietRepo.save(gioHangChiTiet);
    }

    @Override
    public void deleteCartItem(GioHangChiTiet gioHangChiTiet) {
        gioHangChiTietRepo.delete(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet getCartItemByCartItemId(Integer id) {
        return gioHangChiTietRepo.findGioHangChiTietById(id);

    }

    @Override
    public GioHangChiTiet getByCartAndProductDetail(GioHang gioHang, ChiTietSanPham chiTietSanPham) {
        return (GioHangChiTiet) gioHangChiTietRepo.findByGioHangAndChiTietSanPham(gioHang, chiTietSanPham);
    }


    @Override
    public ArrayList<GioHangChiTiet> getAllGHCT() {
        return (ArrayList<GioHangChiTiet>) gioHangChiTietRepository.findAll();
    }

    @Override
    public GioHangChiTiet findByGioHangAndChiTietSanPham(GioHang gioHang, ChiTietSanPham chiTietSanPham) {
        return gioHangChiTietRepository.findByGioHangAndChiTietSanPham(gioHang, chiTietSanPham);
    }

    @Override
    public List<GioHangChiTiet> getGioHangChiTietByGioHangId(Integer gioHangId) {
        return gioHangChiTietRepository.findByGioHang_Id(gioHangId);
    }

    @Override
    public GioHangChiTiet addGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet updateGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public Integer capNhatSoLuong(Integer id, int increment) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(id).orElse(null);

        if (gioHangChiTiet != null) {
            // Cập nhật số lượng
            int newQuantity = gioHangChiTiet.getSoLuong() + increment;
            if (newQuantity <= 0) {
                throw new IllegalArgumentException("Số lượng không thể nhỏ hơn hoặc bằng 0!");
            }

            gioHangChiTiet.setSoLuong(newQuantity);
            gioHangChiTietRepository.save(gioHangChiTiet);

            return newQuantity;
        }
        return null;
    }



    @Override
    public void deleteGioHangChiTiet(Integer id) {
        gioHangChiTietRepository.deleteById(id);
    }

}
