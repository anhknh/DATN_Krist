package com.example.kristp.controller.user;

import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.repository.GioHangChiTietRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.GioHangChiTietService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.service.KhuyenMaiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/gio-hang-chi-tiet")
public class GioHangChiTietController {
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    BanHangService banHangService;
    @Autowired
    KhuyenMaiService khuyenMaiService;

    HoaDon hoaDonSelected = null;
    // Hiển thị giỏ hàng chi tiết
    @GetMapping("/{gioHangId}")
    public String showGioHangChiTiet(@PathVariable Integer gioHangId, Model model, @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon) {
        List<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getGioHangChiTietByGioHangId(gioHangId);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);
        List<HoaDon> hoaDons = hoaDonService.findAllHoaDonCho();
        if (hoaDonSelected == null) {
            hoaDonSelected = hoaDons.get(0);
        } else {
            if(idHoaDon != null) {
                hoaDonSelected = hoaDonService.findHoaDonById(idHoaDon);
            }
        }
        model.addAttribute("hoaDonSelected", hoaDonSelected);

        //khuyến mại
        model.addAttribute("listKM", khuyenMaiService.getAllKhuyenMai());
        return "gio-hang";
    }

    // Thêm giỏ hàng chi tiết mới
//    @PostMapping
//    public String addGioHangChiTiet(@ModelAttribute GioHangChiTiet gioHangChiTiet, Model model) {
//        GioHangChiTiet addedItem = gioHangChiTietService.addGioHangChiTiet(gioHangChiTiet);
//        model.addAttribute("gioHangChiTiet", addedItem);
//        return "redirect:/gio-hang/" + addedItem.getGioHang().getId();  // Chuyển hướng về trang giỏ hàng
//    }

    // Cập nhật giỏ hàng chi tiết
    @PostMapping("/{id}/update-quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Integer id, @RequestBody Map<String, Integer> payload) {
        Integer soLuong = payload.get("soLuong");
        GioHangChiTiet gioHangChiTiet = (GioHangChiTiet) gioHangChiTietRepository.findByGioHang_Id(id);

        if (gioHangChiTiet != null && soLuong > 0) {
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTietService.updateGioHangChiTiet(gioHangChiTiet);
            return ResponseEntity.ok().body("Cập nhật số lượng thành công");
        } else {
            return ResponseEntity.badRequest().body("Không thể cập nhật số lượng");
        }
    }

    // Xóa giỏ hàng chi tiết
    @GetMapping("/xoa-gio-hang-chi-tiet/{id}")
    public String deleteGioHangChiTiet(@PathVariable Integer id, Model model) {
        gioHangChiTietService.deleteGioHangChiTiet(id);
        return "redirect:/gio-hang-chi-tiet/1" ; // Chuyển hướng về trang giỏ hàng
    }
    @GetMapping("/add-khuyen-mai")
    public String addKhuyenMai(HttpServletRequest request,
                               @RequestParam Integer idKhuyenMai,
                               Model model, RedirectAttributes redirectAttributes) {
        if (banHangService.addKhuyenMai(idKhuyenMai, hoaDonSelected)) {
            redirectAttributes.addFlashAttribute("message", "Áp dụng khuyến mại thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Áp dụng khuyến mại thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }
}
