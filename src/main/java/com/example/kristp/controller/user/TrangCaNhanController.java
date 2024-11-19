package com.example.kristp.controller.user;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/quan-ly")
public class TrangCaNhanController {

    @Autowired
    private KhachHangService khachHangService ;

    @Autowired
    private KhachHangRepository khachHangRepository ;
    @GetMapping("/ca-nhan")
    public String caNhan(Model model){
//        KhachHang khachHang = khachHangService.getKHByUserId(Authen.khachHang.getTaiKhoan());
        Optional<KhachHang> khachHang = khachHangRepository.findById(1);
//        TaiKhoan taiKhoan = new TaiKhoan();
//        taiKhoan.setId(1);
//        // Thiết lập các giá trị
//        taiKhoan.setTenDangNhap("new_user");
//        taiKhoan.setMatKhau("secure_password");
//        taiKhoan.setEmail("new_user@example.com");
//        taiKhoan.setTrangThai(Status.ACTIVE); // Gán trạng thái
//        taiKhoan.setChucVu("Khách hàng");
//
//        KhachHang khachHang1 = new KhachHang(1, "Nguyễn Hùng Mạnh" , Status.ACTIVE , taiKhoan);
        model.addAttribute("khachHang" , khachHang.get());
        return "profile-ca-nhan";
    }

    @PostMapping("/update-thong-tin-kh")
    public String updateThongTin(@ModelAttribute("khachHang")KhachHang khachHang){
        System.out.println(khachHang.getTaiKhoan().getMatKhau() + "Mat khâu " + khachHang.getTaiKhoan().getTenDangNhap());
        System.out.println(khachHang.getTenKhachHang() + "Id " + khachHang.getId());
        khachHangService.updateKhachHang(khachHang);
        return "redirect:/quan-ly/ca-nhan";
    }
}
