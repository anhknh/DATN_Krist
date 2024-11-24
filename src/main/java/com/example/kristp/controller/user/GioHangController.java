package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;

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

        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);

        return "gio-hang";
    }
    @GetMapping("/add")
    public String addToCart(@RequestParam("idChiTietSanPham") Integer idChiTietSanPham, @RequestParam(value = "soLuong", required = false) Integer soLuong,
                            HttpServletRequest request, Model model , RedirectAttributes attributes){
        boolean check = gioHangService.addSanPhamToGioHang(idChiTietSanPham, soLuong);
        if (check) {
            attributes.addFlashAttribute("message", "Thêm vào giỏ hàng thành công.");
            attributes.addFlashAttribute("messageType", "alert-success");
            attributes.addFlashAttribute("titleMsg", "Thành công");

            //get url request
            String referer = request.getHeader("referer");
            //reload page
            return "redirect:" + referer;
        }

        attributes.addFlashAttribute("message", "Kiểm tra lại số lượng");
        attributes.addFlashAttribute("messageType", "alert-danger");
        attributes.addFlashAttribute("titleMsg", "Thêm giỏ hàng Thất bại");
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }


}

