package com.example.kristp.controller.user;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.NhanVien;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TaiKhoanService;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    NhanVienRepository nhanVienRepository;

    @GetMapping("/")
    public String home(Model model,HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (Authen.isLoginKhachHang(session)) {
            return "redirect:/user/trang-chu"; // Chuyển hướng đến trang chính sau khi đăng nhập
        } else {
            return "redirect:/dang-nhap"; // Chuyển hướng đến trang đăng nhập
        }
    }

    @GetMapping("/dang-nhap")
    public String dangNhapView() {
        return "view/login/login";
    }

    @GetMapping("/dang-ki")
    public String dangKyView(Model model) {
        return "view/login/register";
    }

    @PostMapping("/dang-nhap-khach-hang")
    public String dangNhapForm(Model model, HttpSession session,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");

        // Kiểm tra đăng nhập trong `taiKhoanService`
        TaiKhoan taiKhoan = taiKhoanService.dangNhap(tenDangNhap, matKhau);
        if (taiKhoan != null) {
            if(taiKhoan.getChucVu().equals("user")) {
                // Tìm `Customer` dựa trên `taiKhoan`
                KhachHang khachHang = khachHangService.getKHByUserId(taiKhoan);
                session.setAttribute("khachHang", khachHang);
                Authen.khachHang = (KhachHang) session.getAttribute("khachHang");

                // Đặt thông báo đăng nhập thành công
                redirectAttributes.addFlashAttribute("message", "Đăng Nhập thành công!");
                redirectAttributes.addFlashAttribute("messageType", "alert-success");
                redirectAttributes.addFlashAttribute("titleMsg", "Thành công");

                return "redirect:/user/trang-chu";
            } else {
                NhanVien nhanVien = nhanVienRepository.findByTaiKhoanAndTrangThai(taiKhoan, Status.ACTIVE);
                session.setAttribute("nhanVien", nhanVien);
                Authen.nhanVien = (NhanVien) session.getAttribute("nhanVien");

                // Đặt thông báo đăng nhập thành công
                redirectAttributes.addFlashAttribute("message", "Đăng Nhập thành công!");
                redirectAttributes.addFlashAttribute("messageType", "alert-success");
                redirectAttributes.addFlashAttribute("titleMsg", "Thành công");

                return "redirect:/quan-ly/thong-ke";
            }
        } else {
            // Đăng nhập thất bại
            redirectAttributes.addFlashAttribute("message", "Kiểm tra lại thông tin đăng nhập!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/dang-nhap";
        }

    }

    @PostMapping("/dang-ki-tai-khoan")
    public String dangKyForm(@ModelAttribute("taiKhoan")TaiKhoan taiKhoan, Model model,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        TaiKhoan taiKhoanNew = taiKhoanService.taoTaiKhoan(taiKhoan, request.getParameter("tenKhachHang"), request.getParameter("sdtKh"));
        if (taiKhoanNew != null) {
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        }
        return "redirect:/dang-ki";
    }
}
