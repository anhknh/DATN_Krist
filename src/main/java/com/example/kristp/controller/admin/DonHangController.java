package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quan-ly")
public class DonHangController {

    @Autowired
    private HoaDonService hoaDonService ;

    @GetMapping("/don-hang")
    public String getPagination(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.CHO_XAC_NHAN);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "choXacNhan");
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/dang-xu-ly")
    public String dangXuLy(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_XU_LY);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangXuLy");
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/dang-giao-hang")
    public String dangGiaoHang(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.DANG_GIAO_HANG);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "dangGiaoHang");
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/hoan-thanh")
    public String hoanThanh(@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){
        Page<HoaDon> hoaDons = hoaDonService.getPaginationHoaDon(pageNo, HoaDonStatus.HOAN_TAT);
        model.addAttribute("HoaDonList" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        model.addAttribute("check" , "hoanThanh");

        return "view-admin/dashbroad/don-hang";
    }



}
