package com.example.kristp.controller.admin;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.enums.Status;
import com.example.kristp.service.DanhMucService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quan-ly/")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService ;
// phần này không dùng
    @GetMapping("/list-danh-muc")
    private String getAllDanhMuc(RedirectAttributes attributes){
        attributes.addFlashAttribute("listDanhMuc" , danhMucService.getAllDanhMuc());
        return "view-admin/dashbroad/danh-muc-dashroad";
    }
    @GetMapping("/pagination-danh-muc")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<DanhMuc> danhMucs = danhMucService.getPaginationDanhMuc(pageNo);
        model.addAttribute("CategoryList" , danhMucs.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , danhMucs.getTotalPages());
        System.out.println(danhMucs.getTotalPages());
        System.out.println("Đã vào đây " +danhMucs.getTotalPages());
        model.addAttribute("categoryCre" , new DanhMuc() );
        return "view-admin/dashbroad/danh-muc-dashroad";
    }
    @PostMapping("/add-danh-muc")
    private String addDanhMuc(@Valid @ModelAttribute("danhmuc") DanhMuc danhMuc , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("danhmuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Thêm mới danh mục không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-danh-muc";
        }
        else if(danhMucService.addDanhMuc(danhMuc) == null){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Tên của danh mục đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-danh-muc";
        }
        danhMucService.addDanhMuc(danhMuc);
        attributes.addFlashAttribute("message" , "Thêm mới danh mục thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-danh-muc";
    }

    @PostMapping("/update-danh-muc")
    private String updateDanhMuc(@Valid @ModelAttribute("danhmuc")DanhMuc danhMuc , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idDanhMuc){
        if(result.hasErrors()){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Cập nhật danh mục không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-danh-muc";
        }
        else if(danhMucService.updateDanhMuc(danhMuc , idDanhMuc) == null){
            attributes.addFlashAttribute("danhMuc" , danhMuc);
            attributes.addFlashAttribute("message" , "Tên của danh mục đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-danh-muc";
        }
        danhMucService.updateDanhMuc(danhMuc , idDanhMuc);
        attributes.addFlashAttribute("message" , "Cập nhật danh mục thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-danh-muc";
    }

    @GetMapping("/delete-danh-muc/{id}")
    private String deleteDanhMuc(@PathVariable("id")Integer idDanhMuc, RedirectAttributes attributes){

            danhMucService.deleteDanhMuc(idDanhMuc);
            attributes.addFlashAttribute("message" , "Thay đổi trạng thái danh mục thành công .");
            attributes.addFlashAttribute("messageType" , "alert-success");
            attributes.addFlashAttribute("titleMsg" , "Thành công");
            return "redirect:/quan-ly/pagination-danh-muc";
    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten-danh-muc")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten , Model model,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<DanhMuc> danhMucs = danhMucService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        System.out.println("Đã vào đây");
        System.out.println(danhMucs.getContent());
        model.addAttribute("CategoryList" , danhMucs.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , danhMucs.getTotalPages());
        model.addAttribute("categoryCre" , new DanhMuc() );
        return "view-admin/dashbroad/danh-muc-dashroad";
    }
//    @PostMapping("/quan-ly/update-trang-thai/{id}")
//    public ResponseEntity<Void> updateTrangThai(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
//        String trangThai = payload.get("trangThai");
//        if ("ACTIVE".equals(trangThai) || "INACTIVE".equals(trangThai)) {
//            // Cập nhật trạng thái trong database
//            danhMucService.updateTrangThai(id, Status.valueOf(trangThai));
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }
}
