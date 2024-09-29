package com.example.kristp.controller.user;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.repository.KhachHangRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.Date;

@Controller
public class DangKiController {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;
    @Autowired
    KhachHangRepository khachHangRepository;

    // Trang đăng ký
    @GetMapping("/dangki")
    public String dangKi(Model model) {
        return "dangki"; // Trả về trang đăng ký
    }


    @PostMapping("/dangki/add")
    public String addTaiKhoan(Model model, HttpServletRequest request) {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String email = request.getParameter("email");
        String matKhau = request.getParameter("matKhau");

        Integer id = taiKhoanRepository.taoTaiKhoan(tenDangNhap, matKhau, email);
        if (id == null) {
            model.addAttribute("error", "Tạo tài khoản thất bại"); // Thêm thông báo lỗi vào model
            return "/dangki"; // Quay lại trang tạo tài khoản
        }

        KhachHang kh = new KhachHang();
        TaiKhoan taiKhoan = taiKhoanRepository.findById(id).orElse(null);
        if (taiKhoan == null) {
            model.addAttribute("error", "Tài khoản không tìm thấy với id: " + id); // Thêm thông báo lỗi vào model
            return  "/dangki";// Quay lại trang tạo tài khoản
        }

        kh.setTaiKhoan(taiKhoan);
        kh.setNgayTao(new Date());
        kh.setNgaySua(new Date());

        try {
            khachHangRepository.save(kh);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lưu khách hàng thất bại"); // Thêm thông báo lỗi vào model
            return "/khach-hang/form"; // Trả về trang form để người dùng có thể xem lại thông tin
        }

        model.addAttribute("khachHang", kh);
        return "khachhang";

    }
    }
