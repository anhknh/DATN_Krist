package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.repository.SanPhamRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class HomeController {

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    DanhMucRepository danhMucRepository;
    @Autowired
    SanPhamYeuThichService sanPhamYeuThichService;


    @GetMapping("/trang-chu")
    public String hello(Model model) {
//        Dữ liệu fake cần chỉnh sửa lại bằng các hàm lấy dữ liệu từ db


        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.getAllCTSP();
        model.addAttribute("chiTietSanPhamList", chiTietSanPhamList);
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
        //hàm format
        model.addAttribute("convertMoney", dataUtils);
        model.addAttribute("top4DanhMuc", danhMucRepository.findTop4ByTrangThaiOrderByNgayTaoDesc(Status.ACTIVE));
        model.addAttribute("top4SanPham", sanPhamRepository.finTop4SanPhamMoi());
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("sanPhamYeuThichService", sanPhamYeuThichService);
        return "view/home/home-page";
    }

}
