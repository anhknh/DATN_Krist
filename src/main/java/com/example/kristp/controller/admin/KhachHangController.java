package com.example.kristp.controller.admin;


import com.example.kristp.entity.KhachHang;

import com.example.kristp.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-ly-khach-hang/")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService ;

    @GetMapping("/pagination-khach-hang")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<KhachHang> khachHangs = khachHangService.getPaginationKhachHang(pageNo);
        model.addAttribute("khachHangList" , khachHangs.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , khachHangs.getTotalPages());
        model.addAttribute("khachHangCre", new KhachHang() {
        });
        return "view-admin/dashbroad/crud-khach-hang";
    }

    @PostMapping("/add-khach-hang")
    private String addKH(@Valid @ModelAttribute("khachHang") KhachHang khachHang , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("khachHang",khachHang);
            attributes.addFlashAttribute("message" , "Thêm mới khách hàng không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-khach-hang/pagination-khach-hang";
        }
        else if(khachHangService.addKhachHang(khachHang) == null){
            attributes.addFlashAttribute("khachHang",khachHang);
            attributes.addFlashAttribute("message" , "Số điện thoại đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-khach-hang/pagination-khach-hang";
        }
        khachHangService.addKhachHang(khachHang);
        attributes.addFlashAttribute("message" , "Thêm mới khách hàng thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-khach-hang/pagination-khach-hang";
    }

    @PostMapping("/update-khach-hang")
    private String updateNV(@Valid @ModelAttribute("khachHang") KhachHang khachHang, BindingResult result, RedirectAttributes attributes, @RequestParam("id") Integer idKhachHang){
        if (khachHangService.updateKhachHang(khachHang, idKhachHang) == null){
            attributes.addFlashAttribute("khachHang",khachHang);
            attributes.addFlashAttribute("message", "Mã khách hàng đã tồn tại");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return  "redirect:/quan-ly-khach-hang/pagination-khach-hang";
        }
        else if(khachHangService.updateKhachHang(khachHang,idKhachHang) == null){
            attributes.addFlashAttribute("khachHang",khachHang);
            attributes.addFlashAttribute("message" , "Số điện thoại đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-khach-hang/pagination-khach-hang";
        }
        khachHangService.updateKhachHang(khachHang, idKhachHang);
        attributes.addFlashAttribute("message" , "Cập nhật khách hàng thành công");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return  "redirect:/quan-ly-khach-hang/pagination-khach-hang";
    }
    @GetMapping("/delete-khach-hang/{id}")
    private String deleteKH(@PathVariable("id")Integer idKhachHang, RedirectAttributes attributes){
        khachHangService.deleteKhachHang(idKhachHang);
        attributes.addFlashAttribute("message" , "Đổi trạng thái khách hàng thành công");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return  "redirect:/quan-ly-khach-hang/pagination-khach-hang";
    }

    @GetMapping("/tim-kiem-tat-ca-khach-hang")
    private String timKiemTatCaTheoTen(  @RequestParam(name = "tenTimKiem", required = false) String ten,
                                         @RequestParam(name = "sdtTimKiem", required = false) String sdt,
                                         @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         Model model){

        String tenKhachHang = (ten != null && !ten.isEmpty()) ? ten : null;
        String sdtKh = (sdt != null && !sdt.isEmpty()) ? sdt : null;

        Page<KhachHang> khachHangs = khachHangService.timTatCaTheoTen(pageNo, tenKhachHang, sdtKh);
        model.addAttribute("khachHangList" , khachHangs.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , khachHangs.getTotalPages());
        model.addAttribute("khachHangCre" , new KhachHang() );
        return "view-admin/dashbroad/crud-khach-hang";
    }
}
