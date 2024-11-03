package com.example.kristp.controller.user;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;

    @GetMapping("/trang-chu")
    public String hello(Model model) {
//        Dữ liệu fake cần chỉnh sửa lại bằng các hàm lấy dữ liệu từ db
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMuc();
        List<CoAo> listCoAo = coAoService.getAllCoAo();

        List<TayAo> listTayAo = tayAoService.getAllTayAo();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "view/home/HomePage";
    }
}
