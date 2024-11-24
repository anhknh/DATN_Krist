package com.example.kristp.controller.user;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/quan-ly")
public class DonHangKhController {

    @Autowired
    private HoaDonService hoaDonService ;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;

    @GetMapping("/don-hang")
    public String getPagination(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.CHO_XAC_NHAN);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "choXacNhan");
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "don-hang";
    }

    @GetMapping("/dang-xu-ly")
    public String dangXuLy(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_XU_LY);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangXuLy");
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "don-hang";
    }

    @GetMapping("/dang-giao-hang")
    public String dangGiaoHang(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_GIAO_HANG);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangGiaoHang");
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "don-hang";
    }

    @GetMapping("/hoan-thanh")
    public String hoanThanh(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.HOAN_TAT);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "hoanThanh");
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "don-hang";
    }



}
