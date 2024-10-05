package com.example.kristp.controller;

import com.example.kristp.entity.SanPham;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.repository.SanPhamRepository;
import com.example.kristp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TimKiemSanPhamController {
    @Autowired
    TimKiemSanPhamService timKiemSanPhamService;

    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;

    @Autowired
    MauSacService mauSacService;

    @Autowired
    TayAoService tayAoService;

    @Autowired
    CoAoService coAoService;
    @Autowired
    SizeService sizeService;



    @GetMapping("/tim-kiem")
    public String timKiemSanPham(@RequestParam(required = false) List<String> tenSanPham,
                                 @RequestParam(required = false) List<Integer> danhMucId,
                                 @RequestParam(required = false) List<Integer> chatLieuId,
                                 @RequestParam(required = false) List<Integer> tayAoId,
                                 @RequestParam(required = false) List<Integer> coAoId,
                                 @RequestParam(required = false) List<Integer> mauSacId,
                                 @RequestParam(required = false) List<Integer> sizeId,
                                 Model model) {


        ArrayList<SanPham> timKiem = timKiemSanPhamService.timKiemSanPham(
                tenSanPham,
                danhMucId,
                chatLieuId,
                tayAoId,
                coAoId,
                mauSacId,
                sizeId
        );


        if (timKiem.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy sản phẩm nào.");
        }

        model.addAttribute("timKiem", timKiem);
        model.addAttribute("danhMucList", danhMucService.getAllDanhMuc());
        model.addAttribute("chatLieuList", chatLieuService.getAllChatLieu());
        model.addAttribute("tayAoList", tayAoService.getAllTayAo());
        model.addAttribute("coAoList", coAoService.getAllCoAo());
        model.addAttribute("mauSacList", mauSacService.getAllMauSac());
        model.addAttribute("sizeList", sizeService.getAllSize());

        return "timkiemsanpham";
    }
}
