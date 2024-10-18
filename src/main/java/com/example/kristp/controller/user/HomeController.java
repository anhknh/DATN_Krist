package com.example.kristp.controller.user;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import com.example.kristp.service.DanhMucService;
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

    @GetMapping("/trang-chu")
    public String hello(Model model) {
//        Dữ liệu fake cần chỉnh sửa lại bằng các hàm lấy dữ liệu từ db
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMuc();
        List<CoAo> listCoAo = new ArrayList<>();
        listCoAo.add(new CoAo(1, "Cổ áo tròn", "Mô tả", Status.ACTIVE));
        listCoAo.add(new CoAo(2, "Cổ áo vuông", "Mô tả 2", Status.ACTIVE));
        listCoAo.add(new CoAo(3, "Cổ áo thuyền", "Mô tả 3", Status.ACTIVE));
        listCoAo.add(new CoAo(4, "Cổ áo polo", "Mô tả 4", Status.ACTIVE));
        listCoAo.add(new CoAo(5, "Cổ áo sơ mi", "Mô tả 5", Status.ACTIVE));
        List<TayAo> listTayAo = new ArrayList<>();

        listTayAo.add(new TayAo(1, "Tay áo dài", "Tay áo dài truyền thống", Status.ACTIVE));
        listTayAo.add(new TayAo(2, "Tay áo ngắn", "Tay áo ngắn năng động", Status.ACTIVE));
        listTayAo.add(new TayAo(3, "Tay áo lửng", "Tay áo lửng thời trang", Status.ACTIVE));
        listTayAo.add(new TayAo(4, "Tay áo raglan", "Tay áo raglan cá tính", Status.ACTIVE));
        listTayAo.add(new TayAo(5, "Tay áo kimono", "Tay áo kimono độc đáo", Status.ACTIVE));

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);
        return "view/home/HomePage";
    }
}
