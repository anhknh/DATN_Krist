package com.example.kristp.controller.user;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.TayAo;
import com.example.kristp.service.*;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.TayAo;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import java.util.List;

@Controller
public class ProductDetailController {

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;

    @GetMapping("/chi-tiet-san-pham")
    public String chiTetSanPham(@RequestParam("idSanPham") Integer idSanPham, Model model) {
        model.addAttribute("sanPham", sanPhamService.findSanphamById(idSanPham));
        model.addAttribute("listCTSP", chiTietSanPhamService.getProductDetailsByProductId(idSanPham));
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("ListColorProductDetail", chiTietSanPhamService.getColorsByProductId(idSanPham));
        model.addAttribute("ListSizeProductDetail", chiTietSanPhamService.getSizesByProductId(idSanPham));


        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "view/product-detail/Product-detail";
    }
}
