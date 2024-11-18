package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.DiaChiService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import com.example.kristp.utils.Pagination;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/user-dia-chi/")
public class DiaChiController {
    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private TaiKhoanService taiKhoanService;



    // Hiển thị danh sách địa chỉ của khách hàng với phân trang
    @GetMapping("/pagination-dia-chi")
    public String getPagination(Model model,@RequestParam("page") Optional<Integer> page) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<DiaChi> diachis = diaChiService.getAllActiveDiaChi(pageable); // Giả sử mỗi trang có 5 địa chỉ
        model.addAttribute("diaChiList", diachis.getContent());
        //phân trang
        model.addAttribute("totalPage", diachis.getTotalPages() - 1);
        model.addAttribute("page", diachis);
        model.addAttribute("pagination", pagination.getPage(diachis.getNumber(), diachis.getTotalPages()));
        model.addAttribute("diaChiCre", new DiaChi());
        return "view-admin/dashbroad/crud-dia-chi";
    }





    @PostMapping("/add-dia-chi")
    public String addDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result,
                            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Thêm mới địa chỉ không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            return "redirect:/user-dia-chi/pagination-dia-chi";
        }

        // Gọi phương thức từ service để thêm địa chỉ
        diaChiService.addDiaChi(diaChi);
        attributes.addFlashAttribute("message", "Thêm mới địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        return "redirect:/user-dia-chi/pagination-dia-chi";
    }

//    @PostMapping("/update-dia-chi")
//    public String updateDiaChi(@Valid @ModelAttribute("diaChi") DiaChi diaChi,
//                               BindingResult result,
//                               RedirectAttributes attributes,
//                               @RequestParam("id") Integer id) {
//        // Gán id cho diaChi
//        diaChi.setId(id);
//
//        // Kiểm tra lỗi trong binding
//        if (result.hasErrors()) {
//            attributes.addFlashAttribute("message", "Cập nhật địa chỉ không thành công.");
//            attributes.addFlashAttribute("messageType", "alert-danger");
//            return "redirect:/user-dia-chi/pagination-dia-chi";
//        }
//
//        // Cập nhật địa chỉ
//        DiaChi updatedDiaChi = diaChiService.updateDiaChi(diaChi);
//
//        if (updatedDiaChi == null) {
//            attributes.addFlashAttribute("message", "Cập nhật địa chỉ không thành công.");
//            attributes.addFlashAttribute("messageType", "alert-danger");
//            return "redirect:/user-dia-chi/pagination-dia-chi";
//        }
//
//        attributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công.");
//        attributes.addFlashAttribute("messageType", "alert-success");
//        return "redirect:/user-dia-chi/pagination-dia-chi";
//    }
@PostMapping("/update-dia-chi")
private String updateDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result, RedirectAttributes attributes) {


    if (result.hasErrors()) {
        attributes.addFlashAttribute("diaChi", diaChi);
        attributes.addFlashAttribute("message", "Cập nhật địa chỉ không thành công.");
        attributes.addFlashAttribute("messageType", "alert-danger");
        attributes.addFlashAttribute("titleMsg", "Thất bại");
        return "redirect:/user-dia-chi/pagination-dia-chi";
    }

    diaChiService.updateDiaChi(diaChi, diaChi.getId());
    attributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công.");
    attributes.addFlashAttribute("messageType", "alert-success");
    attributes.addFlashAttribute("titleMsg", "Thành công");
    return "redirect:/user-dia-chi/pagination-dia-chi";
}


    // Xóa địa chỉ
    @GetMapping("/delete-dia-chi/{id}")
    public String deleteDiaChi(@PathVariable("id") Integer idDiaChi, RedirectAttributes attributes) {
        diaChiService.deleteDiaChi(idDiaChi);
        attributes.addFlashAttribute("message", "Xóa địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        return "redirect:/user-dia-chi/pagination-dia-chi";
    }
}
