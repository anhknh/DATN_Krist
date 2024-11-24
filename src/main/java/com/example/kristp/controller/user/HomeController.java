package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
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
    GioHangService gioHangService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    DataUtils dataUtils;


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

        float tongTien = 0;
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        ArrayList<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
            tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
        }
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);

        model.addAttribute("khachHang", Authen.khachHang);
        model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        //hàm format
        model.addAttribute("convertMoney", dataUtils);
        return "view/home/home-page";
    }

}
