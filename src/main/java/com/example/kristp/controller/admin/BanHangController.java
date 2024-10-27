package com.example.kristp.controller.admin;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quan-ly")
public class BanHangController {

    @Autowired
    TimKiemSanPhamService timKiemSanPhamService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;
    @Autowired
    TayAoService tayAoService;
    @Autowired
    CoAoService coAoService;
    @Autowired
    MauSacService mauSacService;
    @Autowired
    SizeService sizeService;
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    BanHangService banHangService;
    HoaDon hoaDonSelected = null;


    @GetMapping("/ban-hang")
    public String banHang() {
        return "view-admin/dashbroad/ban-hang";
    }
}
