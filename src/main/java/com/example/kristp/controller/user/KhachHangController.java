package com.example.kristp.controller.user;


import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.service.impl.KhachHangServiceImpl;
import com.example.kristp.service.impl.TaiKhoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    KhachHangServiceImpl khachHangServiceImpl;

    @Autowired
    TaiKhoanServiceImpl taiKhoanServiceImpl;

    // Hiển thị danh sách khách hàng
    @GetMapping
    public String listKhachHangs(Model model) {
        model.addAttribute("khachHang", khachHangServiceImpl.getAllKhachHang());
        return "khachhang";
    }
    // Hiển thị form tạo mới
    @GetMapping("/them")
    public String formthemKhachHang(Model model) {
        KhachHang khachHang = new KhachHang(); // Khởi tạo đối tượng mới
        model.addAttribute("khachHang", khachHang);
        return "khachhangform";
    }


    // Lưu khách hàng
    @PostMapping("/luu")
    public String saveKhachHang(@ModelAttribute("khachHang") KhachHang khachHang) {
        khachHangServiceImpl.saveKhachHang(khachHang);
        return "redirect:/khachhang";
    }

    // Hiển thị form cập nhật
    @GetMapping("/chitiet/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        TaiKhoan khachHang = taiKhoanServiceImpl.getUserId(id);
        if (khachHang == null) {
            // Có thể chuyển hướng đến một trang thông báo lỗi hoặc trang danh sách
            return "redirect:/khachhang"; // hoặc một trang khác
        }
        model.addAttribute("khachHang", khachHang);
        return "khachhangchitiet";
    }

    // Cập nhật thông tin khách hàng
    @PostMapping("/capnhat/{id}")
    public String updateKhachHang(@PathVariable(value = "id") int id, @ModelAttribute("khachHang") KhachHang khachHang) {
        TaiKhoan idTK = taiKhoanServiceImpl.getUserId(id);
        KhachHang khachHang1 = khachHangServiceImpl.getKHByUserId(idTK);
        khachHang1.setTenKhachHang(khachHang.getTenKhachHang());
        khachHang1.setTrangThai(khachHang.getTrangThai());
        khachHangServiceImpl.updateKhachHang(khachHang1);
        return "redirect:/khachhang";
    }

    // Xóa khách hàng
    @GetMapping("/xoa/{id}")
    public String deleteKhachHang(@PathVariable("id") int id) {
        khachHangServiceImpl.deleteKhachHang(id);
        return "redirect:/khachhang";
    }

}
