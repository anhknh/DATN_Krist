package com.example.kristp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly")
public class ThongKe {
    @GetMapping("/thong-ke")
    public String test(Model model) throws JsonProcessingException {
        return "view-admin/dashbroad/thong-ke";
    }
}
