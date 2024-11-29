package com.example.kristp.controller.user;

import com.example.kristp.entity.DanhGia;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.service.DanhGiaService;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.utils.Authen;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DanhGiaController {

    @Autowired
    DanhGiaService danhGiaService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;

    @PostMapping("/them-danh-gia")
    public String themDanhGia(Model model,
                              @ModelAttribute DanhGia danhGiaRq, @RequestParam("idHoaDonChiTet") Integer idHoaDonCHiTiet,
                              RedirectAttributes redirectAttributes, HttpServletRequest request) {

        DanhGia danhGia = new DanhGia();
        danhGia.setMucDoDanhGia(danhGiaRq.getMucDoDanhGia());
        danhGia.setNoiDung(danhGiaRq.getNoiDung());
        danhGia.setHoaDonChiTiet(hoaDonChiTietRepo.findById(idHoaDonCHiTiet).orElse(null));
        danhGia.setKhachHang(Authen.khachHang);
        if (danhGiaService.luuDanhGia(danhGia)) {
            redirectAttributes.addFlashAttribute("message", "Gửi đánh giá thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Bạn không có lượt đánh giá sản phẩm này!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }
}
