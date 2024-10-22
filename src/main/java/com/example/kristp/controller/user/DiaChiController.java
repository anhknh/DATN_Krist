package com.example.kristp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DiaChiController {
    @GetMapping("/dia-chi")
    public String diachi() {
        return "view-admin/dashbroad/crud-dia-chi";
    }
}
