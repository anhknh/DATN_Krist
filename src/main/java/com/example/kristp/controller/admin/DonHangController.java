package com.example.kristp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DonHangController {

    @GetMapping("/view-don-hang")
    public String donHang() {
        return "view-admin/dashbroad/don-hang";
    }

}
