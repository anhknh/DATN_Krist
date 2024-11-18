package com.example.kristp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testProfile {

    @GetMapping("/test-profile")
    public String profile(){
        return "view-admin/dashbroad/profile-nhan-nhan";
    }
}
