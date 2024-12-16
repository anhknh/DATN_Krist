package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.DataUtils;
import com.example.kristp.utils.Pagination;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quan-ly/")
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
            @RequestParam("page") Optional<Integer> page,
            Model model) throws JsonProcessingException {

        // Nếu không có giá trị trangThai, mặc định hiển thị tất cả
        if (trangThai == null || trangThai.isEmpty()) {
            trangThai = "0";  // Mặc định là "Tất cả"
        }

        System.out.println("Trạng thái: " + trangThai); // Log để kiểm tra

        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 10);

        // Lấy danh sách đơn hàng theo trạng thái
        Page<HoaDon> hoaDons = hoaDonService.getAllHoaDon(trangThai, pageable);

        // Thêm vào model
        model.addAttribute("hoaDons", hoaDons.getContent());
        model.addAttribute("totalPage", hoaDons.getTotalPages() - 1);
        model.addAttribute("page", hoaDons);
        model.addAttribute("pagination", pagination.getPage(hoaDons.getNumber(), hoaDons.getTotalPages()));
        model.addAttribute("currentTrangThai", trangThai);  // Truyền giá trị trangThai vào model
        // Lấy số lượng đơn hàng theo trạng thái

        //count trang thái
        model.addAttribute("trangThaiCho", hoaDonService.getCountByTrangThai(HoaDonStatus.CHO_XAC_NHAN));
        model.addAttribute("trangThaiXuLy", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_XU_LY));
        model.addAttribute("trangThaiGiao", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_GIAO_HANG));
        model.addAttribute("trangThaiHT", hoaDonService.getCountByTrangThai(HoaDonStatus.HOAN_TAT));
        model.addAttribute("trangThaiHUY", hoaDonService.getCountByTrangThai(HoaDonStatus.DA_HUY));
        model.addAttribute("trangThaiAll", hoaDonRepository.count());
        model.addAttribute("dataUtils", dataUtils);
        return "view-admin/dashbroad/don-hang";
    }

    @GetMapping("/tim-kiem-don-hang")
    public String timKiemHoaDon(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ngayKetThuc,
            @RequestParam("page") Optional<Integer> page,
            Model model) {

        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 10);

        // Xử lý giá trị mặc định cho ngày bắt đầu và ngày kết thúc
        if (ngayBatDau == null) {
            ngayBatDau = LocalDateTime.of(2000, 1, 1, 0, 0); // Ngày bắt đầu mặc định
        }
        if (ngayKetThuc == null) {
            ngayKetThuc = LocalDateTime.now(); // Ngày hiện tại
        }

        Page<HoaDon> danhSachHoaDon = hoaDonService.timKiemHoaDon(id, ngayBatDau, ngayKetThuc, pageable);
        model.addAttribute("hoaDons", danhSachHoaDon.getContent());
        model.addAttribute("totalPage", danhSachHoaDon.getTotalPages() - 1);
        model.addAttribute("page", danhSachHoaDon);
        model.addAttribute("pagination", pagination.getPage(danhSachHoaDon.getNumber(), danhSachHoaDon.getTotalPages()));
        //count trang thái
        model.addAttribute("trangThaiCho", hoaDonService.getCountByTrangThai(HoaDonStatus.CHO_XAC_NHAN));
        model.addAttribute("trangThaiXuLy", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_XU_LY));
        model.addAttribute("trangThaiGiao", hoaDonService.getCountByTrangThai(HoaDonStatus.DANG_GIAO_HANG));
        model.addAttribute("trangThaiHT", hoaDonService.getCountByTrangThai(HoaDonStatus.HOAN_TAT));
        model.addAttribute("trangThaiHUY", hoaDonService.getCountByTrangThai(HoaDonStatus.DA_HUY));
        model.addAttribute("trangThaiAll", hoaDonRepository.count());
        model.addAttribute("dataUtils", dataUtils);
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

    @PostMapping("/doi-phi-van-chuyen")
    public String doiOhiVanChuyen(@RequestParam("orderId") Integer idHoaDon,
                               @RequestParam("shippingFee") double shippingFee,
                               Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(hoaDonService.changePhiVanChuyen(idHoaDon, shippingFee)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật phí vận chuyển thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Trạng thái đã tahy đổi!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    //logic mới

    @PostMapping("/cap-nhat-so-luong-don")
    public String capNhatSoLuong(@RequestParam("inHoaDonChiTiet") Integer idHoaDonChiTiet,
                                 @RequestParam("soLuong") Integer soLuong,
                                 Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(hoaDonService.capNhatSoLuong(idHoaDonChiTiet, soLuong)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật đơn hàng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Vượt quá số lượng hoặc trạng thái đã thay đổi!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @GetMapping("/xoa-san-pham-don")
    public String xoaSanPham(@RequestParam Integer id,
                             Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(hoaDonService.xoaSanPham(id)) {
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm đơn hàng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy hoặc Trạng thái đã thay đổi!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @GetMapping("/lay-thong-tin")
    public ResponseEntity<HoaDon> layThongTinHoaDon(@RequestParam Integer hoaDonId) {
        HoaDon hoaDon = hoaDonService.findHoaDonById(hoaDonId);
        if(hoaDon.getTrangThai() != HoaDonStatus.CHO_XAC_NHAN) {
            return ResponseEntity.badRequest().build();
        }
        // Logic lấy thông tin hóa đơn cập nhật
        return ResponseEntity.ok(hoaDon);
    }

    @GetMapping("/add-san-pham-don")
    public String addSanPhamDon(@RequestParam("idHoaDon") Integer idHoaDon,
                             @RequestParam("idChiTiet") Integer idChiTiet,
                             Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(hoaDonService.themSanPhamDon(idHoaDon, idChiTiet)) {
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm vào đơn thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Vượt quá số lượng hoặc Trạng thái đã thay đổi!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

}
