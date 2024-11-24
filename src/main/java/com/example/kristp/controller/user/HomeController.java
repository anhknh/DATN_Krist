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
    private CoAoService coAoService ;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;




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
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "view/home/home-page";
    }

}
