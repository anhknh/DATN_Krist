package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.TayAoService;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quan-ly")
public class TrangCaNhanController {

    @Autowired
    private KhachHangService khachHangService ;

    @Autowired
    private KhachHangRepository khachHangRepository ;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;


    @GetMapping("/ca-nhan")
    public String caNhan(Model model, HttpSession session){
//        KhachHang khachHang = khachHangService.getKHByUserId(Authen.khachHang.getTaiKhoan());
//        Chưa lấy được id của khách hàng

        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang" , khachHang.get());
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);


        return "profile/ca-nhan";
    }

    @PostMapping("/update-thong-tin-kh")
    public String updateThongTin(@ModelAttribute("khachHang")KhachHang khachHang){
        khachHangService.updateKhachHang(khachHang);
        return "redirect:/quan-ly/ca-nhan";
    }
}
