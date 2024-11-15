package com.example.kristp.controller.user;

import com.example.kristp.entity.DiaChi;
import com.example.kristp.service.DiaChiService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import com.example.kristp.utils.Authen;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dat-hang/")
public class DatHangController {
    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @GetMapping("/dia-chi-giao-hang")
    public String helloDiachigiaohang(Model model, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<DiaChi> diachis = diaChiService.getAllActiveDiaChi(PageRequest.of(pageNo, 1)); // Giả sử mỗi trang có 5 địa chỉ
        model.addAttribute("diaChiList", diachis.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", diachis.getTotalPages());
        model.addAttribute("diaChiCre", new DiaChi());
        return "dia-chi-giao-hang";
    }

    @PostMapping("/add-dia-chi-dat-hang")
    public String addDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result,
                            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Thêm mới địa chỉ không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            return "redirect:/dat-hang/dia-chi-giao-hang";
        }

        // Gọi phương thức từ service để thêm địa chỉ
        diaChiService.addDiaChi(diaChi);
        attributes.addFlashAttribute("message", "Thêm mới địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        return "redirect:/dat-hang/dia-chi-giao-hang";
    }

    @PostMapping("/update-dia-chi-dat-hang")
    private String updateDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("diaChi", diaChi);
            attributes.addFlashAttribute("message", "Cập nhật địa chỉ không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/dat-hang/dia-chi-giao-hang";
        }

        diaChiService.updateDiaChi(diaChi, diaChi.getId());
        attributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/dat-hang/dia-chi-giao-hang";
    }

    // Xóa địa chỉ
    @GetMapping("/delete-dia-chi-dat-hang/{id}")
    public String deleteDiaChi(@PathVariable("id") Integer idDiaChi, RedirectAttributes attributes) {
        diaChiService.deleteDiaChi(idDiaChi);
        attributes.addFlashAttribute("message", "Xóa địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        return "redirect:/dat-hang/dia-chi-giao-hang";
    }


    @GetMapping("/phuong-thuc-thanh-toan")
    public String helloPhuongthucthanhtoan() {
        return "phuong-thuc-thanh-toan";
    }
    @GetMapping("/review")
    public String helloReview() {
        System.out.println(Authen.khachHang.getTenKhachHang());
        return "review";
    }
}
