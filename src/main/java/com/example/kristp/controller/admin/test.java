package com.example.kristp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {
    @GetMapping("/test")
    public String test(Model model) throws JsonProcessingException {
        return "view-admin/dashbroad/thong-ke";
    }
}
