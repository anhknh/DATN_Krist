package com.example.kristp.controller.user;


import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DangNhapController {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;
    @Autowired
    KhachHangRepository khachHangRepository;

    // Trang đăng nhập
    @GetMapping("/dangnhap")
    public String dangNhap(Model model) {
        return "dangnhap"; // Trả về trang đăng nhập
    }

    // Xử lý đăng nhập
    @PostMapping("/dangnhap/add")
    public String xuLyDangNhap(HttpSession session, HttpServletRequest request, Model model) {
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");

        // Tìm tài khoản theo tên đăng nhập
        TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(tenDangNhap);
        if (taiKhoan == null) {
            model.addAttribute("error", "Tài khoản không tồn tại."); // Thêm thông báo lỗi vào model
            return "/dangnhap"; // Quay lại trang đăng nhập với lỗi
            // Trả về trang đăng nhập với lỗi
        }

        // Kiểm tra mật khẩu (giả sử mật khẩu được mã hóa bằng BCrypt)

        if (!matKhau.matches( taiKhoan.getMatKhau())) {
            model.addAttribute("error", "Mật khẩu không chính xác."); // Thêm thông báo lỗi vào model
            return "/dangnhap"; // Quay lại trang đăng nhập với lỗi
            // Trả về trang đăng nhập với lỗi
        }

        // Đăng nhập thành công, lưu thông tin khách hàng vào session
        KhachHang khachHang = khachHangRepository.findByTaiKhoan(taiKhoan);
        session.setAttribute("khachHang", khachHang);

        // Chuyển hướng đến trang chủ khách hàng sau khi đăng nhập thành công
        return "khachhang";
    }

}
