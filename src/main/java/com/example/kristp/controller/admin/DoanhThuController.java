package com.example.kristp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoanhThuController {

    @GetMapping("/admin-doanh-thu")
    public String doanhThu() {
        return "view-admin/dashbroad/doanh-thu-page";
    }
}
