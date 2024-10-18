package com.example.kristp.controller.admin;

import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.MauSac;
import com.example.kristp.enums.Status;
import com.example.kristp.service.KhuyenMaiService;
import com.example.kristp.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/quan-ly-khuyen-mai/")
public class KhuyenMaiController {
    @Autowired
    private KhuyenMaiService khuyenMaiService ;

    @GetMapping("/list-khuyen-mai")
    private String getAllKhuyenMai(RedirectAttributes attributes){
        attributes.addFlashAttribute("listKhuyenMai" ,khuyenMaiService.getAllKhuyenMai());
        return "view-admin/dashbroad/crud-khuyen-mai";
    }

    @GetMapping("/pagination-khuyen-mai")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<KhuyenMai> khuyenMai = khuyenMaiService.getPaginationKhuyenMai(pageNo);
        model.addAttribute("khuyenMaiList" , khuyenMai.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , khuyenMai.getTotalPages());
        model.addAttribute("khuyenMaiCre" , new KhuyenMai() );
        return "view-admin/dashbroad/crud-khuyen-mai";
    }

    @PostMapping("/add-khuyen-mai")
    private String addKhuyenMai(@Valid @ModelAttribute("khuyenMai") KhuyenMai khuyenMai , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("khuyenMai" , khuyenMai);
            attributes.addFlashAttribute("message" , "Thêm mới khuyen mai không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");

            return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
        }
        KhuyenMai existingKhuyenMai = khuyenMaiService.addKhuyenMai(khuyenMai);
        if (existingKhuyenMai == null) {
            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", "Tên hoặc mã khuyến mãi đã tồn tại.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
        }

        attributes.addFlashAttribute("message", "Thêm mới khuyến mãi thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
    }


    @PostMapping("/update-khuyen-mai")
    private String updateKhuyenMai(@Valid @ModelAttribute("khuyenMai")KhuyenMai khuyenMai , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idKhuyenMai){
        if(result.hasErrors()){
            attributes.addFlashAttribute("khuyenMai" ,khuyenMai);
            attributes.addFlashAttribute("message" , "Cập nhật khuyến mãi không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
        }
        KhuyenMai updatedKhuyenMai = khuyenMaiService.updateKhuyenMai(khuyenMai, idKhuyenMai);
        if (updatedKhuyenMai == null) {
            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", "Tên khuyến mãi đã tồn tại.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
        }

        attributes.addFlashAttribute("message", "Cập nhật khuyến mãi thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";
    }

    @GetMapping("/delete-khuyen-mai/{id}")
    private String deleteKhuyenMai(@PathVariable("id")Integer idKhuyenMai ,  RedirectAttributes attributes){
        khuyenMaiService.deleteKhuyenMai(idKhuyenMai);
        attributes.addFlashAttribute("message" , "Xóa khuyễn mãi thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-khuyen-mai/pagination-khuyen-mai";

    }


    @GetMapping("/tim-kiem")
    public String timKiemKhuyenMai(
            @RequestParam(required = false) String maKhuyenMai,
            @RequestParam(required = false) String tenKhuyenMai,
            @RequestParam(required = false) String kieuKhuyenMai,
            @RequestParam(required = false) Float mucGiamToiDa,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) Date ngayBatDau,
            @RequestParam(required = false) Date ngayKetThuc,
            @PageableDefault(size = 5) Pageable pageable, // Số lượng item trên mỗi trang
            Model model) {

        Page<KhuyenMai> khuyenMais = khuyenMaiService.timKiemKhuyenMai(maKhuyenMai, tenKhuyenMai, kieuKhuyenMai,
                mucGiamToiDa, trangThai, ngayBatDau, ngayKetThuc, pageable);

        model.addAttribute("khuyenMaiList", khuyenMais.getContent());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPage", khuyenMais.getTotalPages());
        model.addAttribute("khuyenMaiCre", new KhuyenMai()); // Tạo đối tượng KhuyenMai mới cho form thêm

        return "view-admin/dashbroad/crud-khuyen-mai"; // Đường dẫn đến view tương ứng với khuyến mãi
    }
}
