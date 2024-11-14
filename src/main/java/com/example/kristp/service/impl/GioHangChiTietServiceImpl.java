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
    @Autowired
    GioHangService gioHangService;

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
    public void increaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang) {
        // Lấy giỏ hàng của khách hàng
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(khachHang);
        if (gioHang == null) {
            // Nếu giỏ hàng chưa tồn tại, tạo mới giỏ hàng cho khách
            gioHang = new GioHang();
            gioHang.setKhachHang(khachHang);
            gioHangService.addSanPhamToGioHang(gioHang); // Lưu giỏ hàng
        }

        // Lấy chi tiết giỏ hàng
        List<GioHangChiTiet> cartItems = gioHangChiTietRepo.findByChiTietSanPhamAndGioHang_KhachHang(chiTietSanPham, khachHang);
        GioHangChiTiet cartItem; // Khai báo cartItem

        if (!cartItems.isEmpty()) {
            // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
            cartItem = cartItems.get(0);  // Lấy phần tử đầu tiên trong danh sách
            cartItem.setSoLuong(cartItem.getSoLuong() + 1);
        } else {
            // Nếu sản phẩm chưa có, tạo mới chi tiết giỏ hàng
            cartItem = new GioHangChiTiet(); // Khởi tạo đối tượng mới
            cartItem.setGioHang(gioHang);
            cartItem.setChiTietSanPham(chiTietSanPham);
            cartItem.setSoLuong(1);  // Số lượng bắt đầu là 1
        }

        gioHangChiTietRepo.save(cartItem); // Lưu cartItem vào cơ sở dữ liệu
    }

    @Override
    public void decreaseQuantity(ChiTietSanPham chiTietSanPham, KhachHang khachHang) {
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(khachHang);
        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setKhachHang(khachHang);
            gioHangService.addSanPhamToGioHang(gioHang);
        }

        // Lấy chi tiết giỏ hàng
        List<GioHangChiTiet> cartItems = gioHangChiTietRepo.findByChiTietSanPhamAndGioHang_KhachHang(chiTietSanPham, khachHang);
        GioHangChiTiet cartItem; // Khai báo cartItem

        if (!cartItems.isEmpty()) {
            cartItem = cartItems.get(0); // Lấy phần tử đầu tiên trong danh sách
            if (cartItem.getSoLuong() > 1) {
                cartItem.setSoLuong(cartItem.getSoLuong() - 1);
                gioHangChiTietRepo.save(cartItem); // Lưu cartItem vào cơ sở dữ liệu
            }
        }
    }

    @Override
    public ArrayList<GioHangChiTiet> getAllGHCT() {
        return (ArrayList<GioHangChiTiet>) gioHangChiTietRepository.findAll();
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
    public void deleteGioHangChiTiet(Integer id) {
        gioHangChiTietRepository.deleteById(id);
    }

}
