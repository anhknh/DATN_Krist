package com.example.kristp.controller.user;

import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.SanPhamService;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductDetailController {

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    DataUtils dataUtils;

    @GetMapping("/chi-tiet-san-pham")
    public String chiTetSanPham(@RequestParam("idSanPham") Integer idSanPham, Model model) {
        model.addAttribute("sanPham", sanPhamService.findSanphamById(idSanPham));
        model.addAttribute("listCTSP", chiTietSanPhamService.getProductDetailsByProductId(idSanPham));
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("ListColorProductDetail", chiTietSanPhamService.getColorsByProductId(idSanPham));
        model.addAttribute("ListSizeProductDetail", chiTietSanPhamService.getSizesByProductId(idSanPham));


        return "view/product-detail/Product-detail";
    }
}
