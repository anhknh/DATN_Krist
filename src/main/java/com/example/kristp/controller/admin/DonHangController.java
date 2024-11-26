package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quan-ly-don-hang")
public class DonHangController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    Integer idHoaDonSelected;

    @GetMapping("/view-don-hang")
    public String viewHoaDon(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            Model model) throws JsonProcessingException {

        // Nếu không có giá trị trangThai, mặc định hiển thị tất cả
        if (trangThai == null || trangThai.isEmpty()) {
            trangThai = "on";  // Mặc định là "Tất cả"
        }

        System.out.println("Trạng thái: " + trangThai); // Log để kiểm tra

        // Lấy danh sách đơn hàng theo trạng thái
        List<HoaDon> hoaDons = hoaDonService.getAllHoaDon(trangThai);

        // Thêm vào model
        model.addAttribute("hoaDons", hoaDons);
        model.addAttribute("currentTrangThai", trangThai);  // Truyền giá trị trangThai vào model
        // Lấy số lượng đơn hàng theo trạng thái

        //count trang thái
        model.addAttribute("trangThaiCho", hoaDonService.getCountByTrangThai(HoaDonStatus.CHO_XAC_NHAN));
        model.addAttribute("trangThaiXuLy", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_XU_LY));
        model.addAttribute("trangThaiGiao", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_GIAO_HANG));
        model.addAttribute("trangThaiHT", hoaDonService.getCountByTrangThai(HoaDonStatus.HOAN_TAT));
        model.addAttribute("trangThaiAll", hoaDonRepository.count());
        model.addAttribute("dataUtils", dataUtils);
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/tim-kiem-don-hang")
    public String timKiemHoaDon(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayKetThuc,
            Model model) {

        // Xử lý giá trị mặc định cho ngày bắt đầu và ngày kết thúc
        if (ngayBatDau == null) {
            ngayBatDau = LocalDateTime.of(2000, 1, 1, 0, 0); // Ngày bắt đầu mặc định
        }
        if (ngayKetThuc == null) {
            ngayKetThuc = LocalDateTime.now(); // Ngày hiện tại
        }

        List<HoaDon> danhSachHoaDon = hoaDonService.timKiemHoaDon(id, ngayBatDau, ngayKetThuc);
        model.addAttribute("hoaDons", danhSachHoaDon);
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/view-chi-tiet-don-hang")
    public String chiTietDonHang(@RequestParam("idHoaDon") Integer idHoaDon, Model model) {
        HoaDon hoaDon = hoaDonService.findHoaDonById(idHoaDon);
        idHoaDonSelected = idHoaDon;
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("hoaDonChiTiet", hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDon, null).getContent());
        model.addAttribute("convertMoney", dataUtils);
        model.addAttribute("idHoaDonSelected", idHoaDonSelected);
        return "view-admin/dashbroad/chi-tiet-don-hang";
    }

    @GetMapping("/doi-trang-thai")
    public String changeStatus(@RequestParam("idHoaDon") Integer idHoaDon, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(hoaDonService.changeStatus(idHoaDon)) {
            redirectAttributes.addFlashAttribute("message", "Chuyển trạng thái đơn hàng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Đơn đã hoàn tất hoặc có lỗi xảy ra!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

}
