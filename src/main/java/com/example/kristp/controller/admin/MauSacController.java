package com.example.kristp.controller.admin;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.MauSac;
import com.example.kristp.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-ly-mau-sac/")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService ;

    @GetMapping("/list-mau-sac")
    private String getAllMau(RedirectAttributes attributes){
        attributes.addFlashAttribute("listMauSac" ,mauSacService.getAllMauSac());
        return "view-admin/dashbroad/crud-mau-sac";
    }

    @GetMapping("/pagination-mau-sac")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<MauSac> mau = mauSacService.getPaginationMauSac(pageNo);
        model.addAttribute("mauSacList" , mau.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , mau.getTotalPages());
        model.addAttribute("mauSacCre" , new MauSac() );
        return "view-admin/dashbroad/crud-mau-sac";
    }

    @PostMapping("/add-mau-sac")
    private String addMau(@Valid @ModelAttribute("mauSac") MauSac mauSac , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mauSac" , mauSac);
            attributes.addFlashAttribute("message" , "Thêm mới màu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");

            return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
        }
        else if(mauSacService.addMauSac(mauSac) == null){
            attributes.addFlashAttribute("mauSac" , mauSac);
            attributes.addFlashAttribute("message" , "Tên của màu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");

            return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
        }
        mauSacService.addMauSac(mauSac);
        attributes.addFlashAttribute("message" , "Thêm mới ma thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
    }

    @PostMapping("/update-mau-sac")
    private String updateMauSac(@Valid @ModelAttribute("mauSac")MauSac mauSac , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idMauSac){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mauSac" , mauSac);
            attributes.addFlashAttribute("message" , "Cập nhật màu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
        }
        else if(mauSacService.updateMauSac(mauSac , idMauSac) == null){
            attributes.addFlashAttribute("mauSac", mauSac);
            attributes.addFlashAttribute("message" , "Tên của màu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
        }

        mauSacService.updateMauSac(mauSac , idMauSac) ;
        attributes.addFlashAttribute("message" , "Cập nhật màu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-mau-sac/pagination-mau-sac";
    }

    @GetMapping("/delete-mau-sac/{id}")
    private String deleteMauSac(@PathVariable("id")Integer idMauSac ,  RedirectAttributes attributes){
        mauSacService.deleteMauSac(idMauSac);
        attributes.addFlashAttribute("message" , "Xóa màu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-mau-sac/pagination-mau-sac";

    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten ,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo, Model model){
        Page<MauSac> mau = mauSacService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        model.addAttribute("mauSacList" , mau.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , mau.getTotalPages());
        model.addAttribute("mauSacCre" , new MauSac() );
        return "view-admin/dashbroad/crud-mau-sac";
    }
}
