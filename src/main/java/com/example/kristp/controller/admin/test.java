package com.example.kristp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class test {
    @GetMapping("/test")
    public String test(Model model) throws JsonProcessingException {
        return "view-admin/dashbroad/thong-ke";
    }
}
