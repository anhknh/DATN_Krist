package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
        return "view/home/HomePage";
    }
}
