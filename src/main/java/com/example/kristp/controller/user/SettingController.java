package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/")

public class SettingController {

    @Autowired
    SanPhamYeuThichService sanPhamYeuThichService;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;
    @Autowired
    DataUtils dataUtils;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;


    @GetMapping("/setting")
    public String setting(Model model) {
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

        model.addAttribute("khachHang1", Authen.khachHang);
        model.addAttribute("hoaDonChiTietService", hoaDonChiTietService);
        model.addAttribute("convertMoney", new DataUtils());
        return "user-setting";
    }
}
