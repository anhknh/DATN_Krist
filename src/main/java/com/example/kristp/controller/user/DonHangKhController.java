package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quan-ly")
public class DonHangKhController {
    @Autowired
    private KhachHangRepository khachHangRepository ;
    @Autowired
    private HoaDonService hoaDonService ;

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

    @GetMapping("/don-hang")
    public String getPagination(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Optional<KhachHang> khachHang1 = khachHangRepository.findById(Authen.khachHang.getId());
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.CHO_XAC_NHAN , khachHang1.get().getId());
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "choXacNhan");
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang1" , khachHang.get());
        model.addAttribute("convertMoney", new DataUtils());
        //        Các dữ liệu cần cho header
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
        //hàm format
        return "don-hang";
    }

    @GetMapping("/dang-xu-ly")
    public String dangXuLy(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Optional<KhachHang> khachHang1 = khachHangRepository.findById(Authen.khachHang.getId());
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_XU_LY , khachHang1.get().getId());
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangXuLy");
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang1" , khachHang.get());
        model.addAttribute("convertMoney", new DataUtils());
        //        Các dữ liệu cần cho header
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
        return "don-hang";
    }

    @GetMapping("/dang-giao-hang")
    public String dangGiaoHang(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Optional<KhachHang> khachHang1 = khachHangRepository.findById(Authen.khachHang.getId());
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_GIAO_HANG , khachHang1.get().getId());
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangGiaoHang");
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        System.out.println(khachHang.get().getTenKhachHang());
        model.addAttribute("khachHang1" , khachHang.get());
        model.addAttribute("convertMoney", new DataUtils());

        //        Các dữ liệu cần cho header
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
        //hàm format
        return "don-hang";
    }


    @GetMapping("/hoan-thanh")
    public String hoanThanh(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Optional<KhachHang> khachHang1 = khachHangRepository.findById(Authen.khachHang.getId());
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.HOAN_TAT , khachHang1.get().getId());
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "hoanThanh");

        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang1" , khachHang.get());
        model.addAttribute("convertMoney", new DataUtils());

        //        Các dữ liệu cần cho header
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
        return "don-hang";
    }



}
