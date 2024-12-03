package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;


    @GetMapping("/ca-nhan")
    public String caNhan(Model model){

        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang1" , khachHang.get());
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);

        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = null;
        if(Authen.khachHang != null) {
            GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
            gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
            }
            model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        }

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);

        model.addAttribute("khachHang", khachHang.get());
        //hàm format
        model.addAttribute("convertMoney", new DataUtils());
        return "profile-ca-nhan";
    }

    @PostMapping("/update-thong-tin-kh")
    public String updateThongTin(@ModelAttribute("khachHang")KhachHang khachHang){
        System.out.println("Đã cập nhật" + khachHang.getId());
        khachHangService.updateKhachHang(khachHang);
        return "redirect:/quan-ly/ca-nhan";
    }
}
