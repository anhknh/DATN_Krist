package com.example.kristp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BanHangController {


    @GetMapping("/ban-hang")
    public String banHang() {
        return "view-admin/dashbroad/ban-hang";
    }
}
