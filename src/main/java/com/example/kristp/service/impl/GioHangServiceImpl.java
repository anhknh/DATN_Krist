package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.GioHang;
import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.*;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.GioHangChiTietService;
import com.example.kristp.service.GioHangService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    DataUtils dataUtils;


    @Override
    public GioHang findGioHangByKhachHangId(KhachHang khachHang) {
        return gioHangRepository.findByKhachHangId(khachHang.getId());
    }

    @Override
    public boolean addSanPhamToGioHang(Integer idCTSP, Integer soLuong) {
        soLuong = Objects.requireNonNullElse(soLuong, 1);
        if(soLuong > 20) {
            return false;
        }
        // Lấy giỏ hàng của khách hàng
        GioHang gioHang = gioHangRepository.findByKhachHangId(Authen.khachHang.getId());
        if(!dataUtils.checkTotalCart(gioHang)) {
            return false;
        }
        // Lấy chi tiết sản phẩm
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(idCTSP);
        // Kiểm tra số lượng sản phẩm tồn kho
        int soLuongTonKho = chiTietSanPham.getSoLuong();
        if (soLuongTonKho < soLuong) {
            return false; // Không đủ hàng để thêm vào giỏ
        }
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.findByGioHangAndChiTietSanPham(gioHang, chiTietSanPham);
        if (gioHangChiTiet != null) {
            // Sản phẩm đã có trong giỏ hàng -> tăng số lượng
            int soLuongMoi = gioHangChiTiet.getSoLuong() + soLuong;
            if (soLuongTonKho < soLuongMoi) {
                return false; // Không đủ hàng để tăng số lượng
            }
            gioHangChiTiet.setSoLuong(soLuongMoi);
            gioHangChiTietService.updateGioHangChiTiet(gioHangChiTiet);
        } else {
            // Sản phẩm chưa có trong giỏ hàng -> thêm mới
            GioHangChiTiet gioHangChiTietMoi = new GioHangChiTiet();
            gioHangChiTietMoi.setChiTietSanPham(chiTietSanPham);
            gioHangChiTietMoi.setSoLuong(soLuong);
            gioHangChiTietMoi.setGioHang(gioHang);
            gioHangChiTietService.addGioHangChiTiet(gioHangChiTietMoi);
        }

        return true; // Thêm sản phẩm thành công
    }


    @Override
    public void deleteGioHangById(List<Integer> id) {
        deleteGioHangById(id);
    }

    @Override
    public Integer countCartItem() {
        return gioHangChiTietRepository.countGioHangChiTietByGioHang(gioHangRepository.findByKhachHangId(Authen.khachHang.getId()));
    }

}
