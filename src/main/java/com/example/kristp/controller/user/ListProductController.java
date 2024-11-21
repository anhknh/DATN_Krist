package com.example.kristp.controller.user;


import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.SanPham;
import com.example.kristp.service.GioHangService;
import com.example.kristp.service.SanPhamService;
import com.example.kristp.service.TimKiemSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListProductController {
    @Autowired
    private SanPhamService sanPhamService ;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    TimKiemSanPhamService timKiemSanPhamService;


    @GetMapping("/danh-sach-san-pham")
    public String danhSachPham(@RequestParam(value = "coAo",required = false) List<String> coAo,
                               @RequestParam(value = "gia", required = false) List<String> gia,
                               @RequestParam(value = "size",required = false) List<String> size,
                               @RequestParam(value = "tenSanPham",required = false) String tenSanPham,
                               @RequestParam(value = "mauSac",required = false) List<String> mauSac,
                               @RequestParam(value = "pageNo" , defaultValue = "0")Integer pageNo , Model model) {


        Page<SanPham> sanPhams = sanPhamService.getPaginationSanPham(pageNo);
//        Page<SanPham> listSanPham = timKiemSanPhamService.timKiemSanPham(tenSanPham,null,null,
//                null,null,null,null,pageNo);
        model.addAttribute("productList" , sanPhams.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , sanPhams.getTotalPages());
        model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        return "view/list-product/list-product-page";
    }
}
