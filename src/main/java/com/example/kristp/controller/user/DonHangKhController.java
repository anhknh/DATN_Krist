package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.service.TayAoService;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/don-hang")
    public String getPagination(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Optional<KhachHang> khachHang1 = khachHangRepository.findById(Authen.khachHang.getId());
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.CHO_XAC_NHAN , khachHang1.get().getId());
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "choXacNhan");
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang" , khachHang.get());
        model.addAttribute("dataUtils", new DataUtils());
        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        model.addAttribute("dataFormat", dataUtils);
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
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang" , khachHang.get());
        model.addAttribute("dataUtils", new DataUtils());
        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
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
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        System.out.println(khachHang.get().getTenKhachHang());
        model.addAttribute("khachHang" , khachHang.get());
        model.addAttribute("dataUtils", new DataUtils());
        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
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
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang" , khachHang.get());
        model.addAttribute("dataUtils", new DataUtils());
        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "don-hang";
    }



}
