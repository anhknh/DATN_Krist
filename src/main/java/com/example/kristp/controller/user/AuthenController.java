package com.example.kristp.controller.user;

import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.service.TaiKhoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenController {

    @Autowired
    TaiKhoanService taiKhoanService;

    @GetMapping("/dang-nhap")
    public String dangNhapView() {
        return "view/login/login";
    }

    @GetMapping("/dang-ki")
    public String dangKyView(Model model) {
        return "view/login/register";
    }

    @PostMapping("/dang-nhap-khach-hang")
    public String dangNhapForm(Model model,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) {
        TaiKhoan taiKhoanDangNhap = taiKhoanService.dangNhap(request.getParameter("tenDangNhap"),
                                                            request.getParameter("matKhau"));
        if (taiKhoanDangNhap != null) {
            redirectAttributes.addFlashAttribute("message", "Đăng Nhập thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
            return "redirect:/trang-chu";
        }
        redirectAttributes.addFlashAttribute("message", "Kiểm tra lại thông tin đăng nhập!");
        redirectAttributes.addFlashAttribute("messageType", "alert-danger");
        redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        return "redirect:/dang-nhap";
    }

    @PostMapping("/dang-ki-tai-khoan")
    public String dangKyForm(@ModelAttribute("taiKhoan")TaiKhoan taiKhoan, Model model,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        TaiKhoan taiKhoanNew = taiKhoanService.taoTaiKhoan(taiKhoan, request.getParameter("tenKhachHang"));
        if (taiKhoanNew != null) {
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        }
        return "redirect:/dang-ki";
    }
}
