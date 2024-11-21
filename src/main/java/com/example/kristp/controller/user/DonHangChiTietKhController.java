package com.example.kristp.controller.user;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
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

@Controller
@RequestMapping("/quan-ly")
public class DonHangChiTietKhController {
    @Autowired
    private HoaDonService hoaDonService ;
    @Autowired
    DataUtils dataUtils;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService ;
    @GetMapping("/don-hang-chi-tiet-kh")
    public String getPagination(@RequestParam(name = "id" , defaultValue = "0")Integer id , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model , RedirectAttributes attributes){
        Pageable pageable = PageRequest.of(pageNo , 3);
        HoaDon hoaDon = hoaDonService.findHoaDonById(id);

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
        model.addAttribute("convertMoney", dataUtils);
        System.out.println(hoaDons.getContent().get(0).getChiTietSanPham().getMau().getMaMauSac());
        return "don-hang-chi-tiet-kh";
    }
}
