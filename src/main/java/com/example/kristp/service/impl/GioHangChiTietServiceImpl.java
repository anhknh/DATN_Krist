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
import com.example.kristp.utils.DataUtils;
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
    @Autowired
    DataUtils dataUtils;

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
        GioHang gioHang = gioHangChiTiet.getGioHang();
        if(increment > 0) {
            if( !dataUtils.checkTotalCart(gioHang)) {
                return null;
            }
        }
        if (gioHangChiTiet != null) {
            ChiTietSanPham chiTietSanPham = gioHangChiTiet.getChiTietSanPham();
            // Cập nhật số lượng
            int newQuantity = gioHangChiTiet.getSoLuong() + increment;
            if(chiTietSanPham.getSoLuong() < newQuantity) {
                return null;
            }
            if (newQuantity <= 0) {
                return null;
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
