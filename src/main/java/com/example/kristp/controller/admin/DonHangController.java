package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        // Chuyển Map<HoaDonStatus, Long> thành JSON String
        Map<HoaDonStatus, Long> countByTrangThai = hoaDonService.getCountByTrangThai();

        // Kiểm tra dữ liệu countByTrangThai để chắc chắn nó không null
        if (countByTrangThai != null) {
            System.out.println("Count By Trang Thai in Controller: " + countByTrangThai);
        } else {
            System.out.println("countByTrangThai is null");
        }

        // Chuyển Map<HoaDonStatus, Long> thành chuỗi JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String countJson = null;
        try {
            // Sử dụng ObjectMapper để chuyển Map thành JSON String
            // Nếu bạn muốn hiển thị giá trị (0, 1, 2...) thay vì tên enum, bạn cần sử dụng value() của enum
            Map<String, Long> stringMap = countByTrangThai.entrySet().stream()
                    .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey().getValue()), Map.Entry::getValue));

            countJson = objectMapper.writeValueAsString(stringMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Truyền JSON vào model
        model.addAttribute("countJson", countJson);
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
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("hoaDonChiTiet", hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDon, null).getContent());
        model.addAttribute("convertMoney", dataUtils);
        return "view-admin/dashbroad/chi-tiet-don-hang";
    }

}
