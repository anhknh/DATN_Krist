package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;


    @Autowired
    private CoAoService coAoService ;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @GetMapping("/trang-chu")
    public String hello(Model model) {
//        Dữ liệu fake cần chỉnh sửa lại bằng các hàm lấy dữ liệu từ db


        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.getAllCTSP();
        model.addAttribute("chiTietSanPhamList", chiTietSanPhamList);
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMuc();
        List<CoAo> listCoAo = coAoService.getAllCoAo();

        List<TayAo> listTayAo = tayAoService.getAllTayAo();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "view/home/home-page";
    }
    @GetMapping
    public String hienThiModalTrangChu(Model model) {
        float tongTien = 0;
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
        }
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);
        return "view/home/home-page";
    }

    @PostMapping("/add")
    public String addFromTrangChu(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham, @RequestParam("soLuong") Optional<Integer> soLuong, HttpServletRequest request, Model model){
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
}
