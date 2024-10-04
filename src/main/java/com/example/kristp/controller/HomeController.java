package com.example.kristp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/trang-chu")
    public String hello() {
        return "view/home/HomePage";
    }

    @GetMapping("/san-pham")
    public String hellooooo() {
        return "DanhSachSP";
    }
}
