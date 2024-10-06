package com.example.kristp.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListProductController {

    @GetMapping("/danh-sach-san-pham")
    public String danhSachPham(@RequestParam(value = "coAo",required = false) List<String> coAo,
                               @RequestParam(value = "gia", required = false) List<String> gia,
                               @RequestParam(value = "size",required = false) List<String> size,
                               @RequestParam(value = "tenSanPham",required = false) String tenSanPham,
                               @RequestParam(value = "mauSac",required = false) List<String> mauSac) {
        System.out.println(coAo);
        System.out.println(gia);
        System.out.println(size);
        System.out.println(mauSac);
        System.out.println(tenSanPham);
        return "view/list-product/list-product-page";
    }
}
