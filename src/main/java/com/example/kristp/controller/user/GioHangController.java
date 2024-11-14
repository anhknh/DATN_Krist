package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.GioHangChiTietService;
import com.example.kristp.service.GioHangService;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;


    // Hiển thị giỏ hàng của khách hàng
    @GetMapping
    public String hienThiGioHang(Model model) {
        float tongTien = 0;
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
        }
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);
        return "gio-hang";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham, @RequestParam("soLuong") Optional<Integer> soLuong, HttpServletRequest request, Model model){
        // Default quantity is 1 if not provided
        Integer soLuongCheck = soLuong.orElse(1);

        // Kiểm tra giỏ hàng của khách hàng
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);

        // Nếu giỏ hàng chưa có, tạo mới giỏ hàng
        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setKhachHang(Authen.khachHang);
            gioHang.setNgayTao(new Date());
            gioHang.setNgaySua(new Date());
            gioHang.setTongTien(BigDecimal.valueOf(0.00f));  // Khởi tạo tổng tiền
            gioHangService.addSanPhamToGioHang(gioHang); // Lưu giỏ hàng mới
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        GioHangChiTiet gioHangChiTietCheck = null;
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            if(gioHangChiTiet.getChiTietSanPham().getId().equals(idChiTietSanPham)) {
                gioHangChiTietCheck = gioHangChiTiet;
                break;
            }
        }

        // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới chi tiết giỏ hàng
        if(gioHangChiTietCheck == null) {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPhamService.getCTSPById(idChiTietSanPham));
            gioHangChiTiet.setNgayTao(new Date());
            gioHangChiTiet.setNgaySua(new Date());
            gioHangChiTiet.setSoLuong(soLuongCheck);
            gioHangChiTietService.saveCartItem(gioHangChiTiet);
        } else {
            // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
            gioHangChiTietCheck.setSoLuong(gioHangChiTietCheck.getSoLuong() + soLuongCheck);
            gioHangChiTietService.saveCartItem(gioHangChiTietCheck);
        }

        // Sau khi xử lý, quay lại trang giỏ hàng
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

    // Thêm sản phẩm vào giỏ hàng
//    @PostMapping("/add")
//    public String addToCart(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham, @RequestParam("soLuong") Optional<Integer> soLuong, HttpServletRequest request, Model model){
//        GioHangChiTiet gioHangChiTietCheck = null;
//        Integer soLuongCheck = soLuong.orElse(1);
//        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
//        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang);
//        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
//            if(gioHangChiTiet.getChiTietSanPham().getId().equals(idChiTietSanPham)) {
//                gioHangChiTietCheck = gioHangChiTiet;
//                break;
//            }
//        }
//        if(gioHangChiTietCheck == null) {
//            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
//            gioHangChiTiet.setGioHang(gioHangService.findGioHangByKhachHangId(Authen.khachHang));
//            gioHangChiTiet.setChiTietSanPham(chiTietSanPhamService.getCTSPById(idChiTietSanPham));
//            gioHangChiTiet.setNgayTao(new Date());
//            gioHangChiTiet.setNgaySua(new Date());
//            gioHangChiTiet.setSoLuong(soLuongCheck);
//            gioHangChiTietService.saveCartItem(gioHangChiTiet);
//        } else {
//            gioHangChiTietCheck.setSoLuong(gioHangChiTietCheck.getSoLuong() + soLuongCheck); ;
//            gioHangChiTietService.saveCartItem(gioHangChiTietCheck);
//        }
//        //get url request
//        String referer = request.getHeader("referer");
//        //reload page
//        return "redirect:" +referer;
//    }

    // Xóa sản phẩm khỏi giỏ hàng
//    @PostMapping("/delete")
//    public String deleteCart(@RequestParam("idGioHangChiTiet") Integer idGioHangChiTiet, HttpServletRequest request) {
//        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getCartItemByCartItemId(idGioHangChiTiet);
//        if(gioHangChiTiet != null) {
//            gioHangChiTietService.deleteCartItem(gioHangChiTiet);
//        }
//        //get url request
//        String referer = request.getHeader("referer");
//        //reload page
//        return "redirect:" +referer;
//    }
    @PostMapping("/delete")
    public String deleteCart(@RequestParam("idGioHangChiTiet") Integer idGioHangChiTiet, HttpServletRequest request) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getCartItemByCartItemId(idGioHangChiTiet);
        if (gioHangChiTiet != null) {
            // Xóa sản phẩm khỏi giỏ hàng
            gioHangChiTietService.deleteCartItem(gioHangChiTiet);

            // Cập nhật lại tổng tiền giỏ hàng
            GioHang gioHang = gioHangChiTiet.getGioHang();
            float tongTien = 0;
            ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
            for (GioHangChiTiet item : gioHangChiTietList) {
                tongTien += (item.getChiTietSanPham().getDonGia() * item.getSoLuong());
            }
            gioHang.setTongTien(BigDecimal.valueOf(tongTien));
            gioHangService.addSanPhamToGioHang(gioHang); // Lưu lại giỏ hàng đã cập nhật
        }

        // Quay lại trang giỏ hàng
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }
    // Tăng số lượng sản phẩm
//    @PostMapping("/tangsoluong")
//    public ResponseEntity<String> tangSoLuong(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham) {
//        gioHangChiTietService.increaseQuantity(chiTietSanPhamService.getCTSPById(idChiTietSanPham),Authen.khachHang);
//        return ResponseEntity.ok("Thêm số lượng thành công");
//    }

    @PostMapping("/tangsoluong")
    public String tangSoLuong(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham) {
        // Tăng số lượng sản phẩm trong giỏ hàng
        gioHangChiTietService.increaseQuantity(chiTietSanPhamService.getCTSPById(idChiTietSanPham), Authen.khachHang);

        // Cập nhật lại tổng tiền giỏ hàng
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet item : gioHangChiTietList) {
            tongTien += (item.getChiTietSanPham().getDonGia() * item.getSoLuong());
        }
        gioHang.setTongTien(BigDecimal.valueOf(tongTien));
        gioHangService.addSanPhamToGioHang(gioHang); // Lưu lại giỏ hàng đã cập nhật

        return "redirect:/gio-hang";
    }
    // Giảm số lượng sản phẩm
//    @PostMapping("/giamsoluong")
//    public ResponseEntity<String> giamSoLuong(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham) {
//        gioHangChiTietService.decreaseQuantity(chiTietSanPhamService.getCTSPById(idChiTietSanPham),Authen.khachHang);
//        return ResponseEntity.ok("Giảm số lượng thành công");
//    }
    @PostMapping("/giamsoluong")
    public String giamSoLuong(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham) {
        // Giảm số lượng sản phẩm trong giỏ hàng
        gioHangChiTietService.decreaseQuantity(chiTietSanPhamService.getCTSPById(idChiTietSanPham), Authen.khachHang);

        // Cập nhật lại tổng tiền giỏ hàng
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet item : gioHangChiTietList) {
            tongTien += (item.getChiTietSanPham().getDonGia() * item.getSoLuong());
        }
        gioHang.setTongTien(BigDecimal.valueOf(tongTien));
        gioHangService.addSanPhamToGioHang(gioHang); // Lưu lại giỏ hàng đã cập nhật

        return "redirect:/gio-hang";
    }
//    @PostMapping("/check-out")
//    public String CartCheckOut(@RequestParam("tongTien") float tongTien, @RequestParam(name = "sanPhamCheck", required = false) List<String> sanPhamCheck, Model model, HttpServletRequest servlet) {
//        Integer idOrder;
//        if(sanPhamCheck == null || sanPhamCheck.isEmpty()) {
//            return "redirect:/gio-hang";
//        }
//        String productCheckParam = String.join(",", sanPhamCheck);
//        return "redirect:/ecommerce/checkout?productCheck=" + productCheckParam;
//    }

}

