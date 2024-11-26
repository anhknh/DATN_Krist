package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quan-ly")
public class DonHangChiTietKhController {

    @Autowired
    GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    private HoaDonService hoaDonService ;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService ;
    @GetMapping("/don-hang-chi-tiet-kh")
    public String getPagination(@RequestParam(name = "id" , defaultValue = "0")Integer id , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model , RedirectAttributes attributes){
        System.out.println("Đã vào đây đơn hàng chi tiết");
        Pageable pageable = PageRequest.of(pageNo , 3);
        HoaDon hoaDon = hoaDonService.findHoaDonById(id);
        System.out.println(id);
        Page<HoaDonChiTiet> hoaDons = hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDon , pageable);
        if(hoaDons.isEmpty()){
            attributes.addFlashAttribute("message" , "Hiện tại không có chi tiết hóa đơn nào trong hóa đơn .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            if(hoaDon.getTrangThai() == HoaDonStatus.CHO_XAC_NHAN){
                return "redirect:/quan-ly/don-hang";
            }
            else if(hoaDon.getTrangThai() == HoaDonStatus.DANG_XU_LY){
                return "redirect:/quan-ly/dang-xu-ly";
            }
            else if(hoaDon.getTrangThai() == HoaDonStatus.DANG_GIAO_HANG){
                return "redirect:/quan-ly/dang-giao-hang";
            }
            else {
                return "redirect:/quan-ly/hoan-thanh";
            }
        }

        Float tongThanhToan = Float.parseFloat(String.valueOf(hoaDon.getTongTien())) + hoaDon.getPhiVanChuyen();
        model.addAttribute("tongThanhToan" , tongThanhToan);
        model.addAttribute("hoaDon" , hoaDon);
        model.addAttribute("donHangChiTiet" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        // Hiển thị danh mục
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);

        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = null;
        if(Authen.khachHang != null) {
            GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
            gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
            }
            model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        }

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);

        model.addAttribute("khachHang", Authen.khachHang);
        model.addAttribute("convertMoney", new DataUtils());
        System.out.println(hoaDons.getContent().get(0).getChiTietSanPham().getMau().getMaMauSac());
        return "don-hang-chi-tiet-kh";
    }
}
