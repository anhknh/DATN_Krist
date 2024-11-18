package com.example.kristp.controller.user;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quan-ly")
public class DonHangChiTietKhController {
    @Autowired
    private HoaDonService hoaDonService ;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService ;
    @GetMapping("/don-hang-chi-tiet-kh")
    public String getPagination(@RequestParam(name = "id" , defaultValue = "0")Integer id , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo , Model model){        Pageable pageable = PageRequest.of(pageNo , 3);
        HoaDon hoaDon = hoaDonService.findHoaDonById(id);
        Page<HoaDonChiTiet> hoaDons = hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDon , pageable);
        model.addAttribute("tongThanhToan" , hoaDon.getTongTien());
        model.addAttribute("hoaDon" , hoaDon);
        model.addAttribute("donHangChiTiet" , hoaDons.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , hoaDons.getTotalPages());
        System.out.println(hoaDons.getContent().get(0).getChiTietSanPham().getMau().getMaMauSac());
        return "don-hang-chi-tiet-kh";
    }
}
