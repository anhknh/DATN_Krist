package com.example.kristp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DatHangController {
    @GetMapping("/dia-chi-giao-hang")
    public String helloDiachigiaohang() {
        return "dia-chi-giao-hang";
    }
    @GetMapping("/phuong-thuc-thanh-toan")
    public String helloPhuongthucthanhtoan() {
        return "phuong-thuc-thanh-toan";
    }
    @GetMapping("/review")
    public String helloReview() {
        return "review";
    }
}
