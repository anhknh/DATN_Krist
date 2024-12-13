package com.example.kristp.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly/")
public class SetttingController {


    @GetMapping("/setting")
    public String setting() {
        return "view-admin/dashbroad/admin-setting";
    }
}
