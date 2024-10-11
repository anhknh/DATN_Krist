package com.example.kristp.controller.admin;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.service.DanhMucService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DanhMucController {
        @Autowired
        private DanhMucService danhMucService ;

    @GetMapping("/list-danh-muc")
    private String getAllDanhMuc(RedirectAttributes attributes){
        attributes.addFlashAttribute("listDanhMuc" , danhMucService.getAllDanhMuc());
        return "";
    }

    @PostMapping("/add-danh-muc")
    private String addDanhMuc(@Valid @ModelAttribute("danhmuc") DanhMuc danhMuc , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("danhmuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Thêm mới danh mục không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "danhMucForm";
        }
        else if(danhMucService.addDanhMuc(danhMuc) != null){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Tên của danh mục đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }
        danhMucService.addDanhMuc(danhMuc);
        attributes.addFlashAttribute("message" , "Thêm mới danh mục thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titileMsg" , "Thành công");
        return "themThanhCong";
    }

    @PostMapping("/update-danh-muc")
    private String updateDanhMuc(@Valid @ModelAttribute("danhmuc")DanhMuc danhMuc , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idDanhMuc){
        if(result.hasErrors()){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Cập nhật chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "danhMucForm";
        }
        else if(danhMucService.updateDanhMuc(danhMuc , idDanhMuc) != null){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Tên của danh mục đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }
        danhMucService.updateDanhMuc(danhMuc , idDanhMuc);
        attributes.addFlashAttribute("message" , "Cập nhật danh mục thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titileMsg" , "Thành công");
        return "capNhatThanhCong";
    }


    @PostMapping("/delete-danh-muc/{id}")
    private String deleteDanhMuc(@PathVariable("id")Integer idDanhMuc, RedirectAttributes attributes){
        try {
            danhMucService.deleteDanhMuc(idDanhMuc);
            attributes.addFlashAttribute("message" , "Xóa danh mục thành công .");
            attributes.addFlashAttribute("messageType" , "alert-success");
            attributes.addFlashAttribute("titileMsg" , "Thành công");
            return "redirect/trangDanhMuc";
        }catch (Exception e){
            attributes.addFlashAttribute("message" , "Xóa danh mục không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "redirect/trangDanhMuc";
        }
    }
}
