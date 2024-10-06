package com.example.kristp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {

    @GetMapping("/chi-tiet-san-pham")
    public String chiTetSanPham() {
        return "view/product-detail/Product-detail";
    }
}
