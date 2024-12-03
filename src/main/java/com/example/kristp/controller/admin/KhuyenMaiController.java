package com.example.kristp.controller.admin;

import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.MauSac;
import com.example.kristp.enums.Status;
import com.example.kristp.service.KhuyenMaiService;
import com.example.kristp.service.MauSacService;
import com.example.kristp.utils.DataUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.Date;

import static org.thymeleaf.util.NumberUtils.formatCurrency;

@Controller
@RequestMapping("/quan-ly/")
public class KhuyenMaiController {
    @Autowired
    private KhuyenMaiService khuyenMaiService;
    @Autowired
    private DataUtils dataUtils;

    @GetMapping("/list-khuyen-mai")
    private String getAllKhuyenMai(RedirectAttributes attributes) {
        attributes.addFlashAttribute("listKhuyenMai", khuyenMaiService.getAllKhuyenMai());
        return "view-admin/dashbroad/khuyen-mai";
    }

    @GetMapping("/pagination-khuyen-mai")
    private String getPagination(Model model, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<KhuyenMai> khuyenMai = khuyenMaiService.getPaginationKhuyenMai(pageNo);
        model.addAttribute("khuyenMaiList", khuyenMai.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", khuyenMai.getTotalPages());
        model.addAttribute("khuyenMaiCre", new KhuyenMai());
        model.addAttribute("DataUtils", new DataUtils());
        return "view-admin/dashbroad/khuyen-mai";
    }


    @GetMapping("/pagination-update-khuyen-mai/{id}")
    private String getPagination3(Model model, @PathVariable("id") Integer id,
                                  @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        KhuyenMai khuyenMai = khuyenMaiService.getKhuyenMaiById(id);
        if (khuyenMai == null) {
            // Bạn có thể xử lý trường hợp không tìm thấy khuyến mãi, ví dụ:
            model.addAttribute("message", "Không tìm thấy khuyến mãi.");
            model.addAttribute("messageType", "alert-danger");
            return "view-admin/dashbroad/error-page"; // Chuyển đến một trang lỗi hoặc trang danh sách
        }

        Page<KhuyenMai> khuyenMaiPage = khuyenMaiService.getPaginationKhuyenMai(pageNo);
        model.addAttribute("khuyenMaiList", khuyenMaiPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", khuyenMaiPage.getTotalPages());
        model.addAttribute("khuyenMaiCre", khuyenMai);
        model.addAttribute("DataUtils", new DataUtils());

        return "view-admin/dashbroad/form-edit-khuyen-mai";
    }

    @GetMapping("/pagination-detail-khuyen-mai/{id}")
    private String getPagination4(Model model, @PathVariable("id") Integer id,
                                  @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        KhuyenMai khuyenMai = khuyenMaiService.getKhuyenMaiById(id);
        if (khuyenMai == null) {
            // Bạn có thể xử lý trường hợp không tìm thấy khuyến mãi, ví dụ:
            model.addAttribute("message", "Không tìm thấy khuyến mãi.");
            model.addAttribute("messageType", "alert-danger");
            return "view-admin/dashbroad/error-page"; // Chuyển đến một trang lỗi hoặc trang danh sách
        }

        Page<KhuyenMai> khuyenMaiPage = khuyenMaiService.getPaginationKhuyenMai(pageNo);
        model.addAttribute("khuyenMaiList", khuyenMaiPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", khuyenMaiPage.getTotalPages());
        model.addAttribute("khuyenMaiCre", khuyenMai);
        model.addAttribute("DataUtils", new DataUtils());

        return "view-admin/dashbroad/form-chi-tiet-khuyen-mai";
    }

    @GetMapping("/pagination-add-khuyen-mai")
    private String getPagination2(Model model, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        Page<KhuyenMai> khuyenMai = khuyenMaiService.getPaginationKhuyenMai(pageNo);
        model.addAttribute("khuyenMaiList", khuyenMai.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", khuyenMai.getTotalPages());
        model.addAttribute("khuyenMaiCre", new KhuyenMai());
        model.addAttribute("DataUtils", new DataUtils());
        return "view-admin/dashbroad/form-them-khuyen-mai";
    }

//    @PostMapping("/add-khuyen-mai")
//    private String addKhuyenMai(@Valid @ModelAttribute("khuyenMai") KhuyenMai khuyenMai , BindingResult result , RedirectAttributes attributes){
//        if(result.hasErrors()){
//            attributes.addFlashAttribute("khuyenMai" , khuyenMai);
//            attributes.addFlashAttribute("message" , "Thêm mới khuyến mãi không thành công .");
//            attributes.addFlashAttribute("messageType" , "alert-danger");
//            attributes.addFlashAttribute("titleMsg" , "Thất bại");
//            return "redirect:/quan-ly/pagination-add-khuyen-mai";
//        }
//        KhuyenMai existingKhuyenMai = khuyenMaiService.addKhuyenMai(khuyenMai);
//        if (existingKhuyenMai == null) {
//            attributes.addFlashAttribute("khuyenMai", khuyenMai);
//            attributes.addFlashAttribute("message", "Tên hoặc mã khuyến mãi đã tồn tại.");
//            attributes.addFlashAttribute("messageType", "alert-danger");
//            attributes.addFlashAttribute("titleMsg", "Thất bại");
//            return "redirect:/quan-ly/pagination-add-khuyen-mai";
//        }
//
//        attributes.addFlashAttribute("message", "Thêm mới khuyến mãi thành công.");
//        attributes.addFlashAttribute("messageType", "alert-success");
//        attributes.addFlashAttribute("titleMsg", "Thành công");
//        return "redirect:/quan-ly/pagination-add-khuyen-mai";
//    }

    @PostMapping("/add-khuyen-mai")
    private String addKhuyenMai(@Valid @ModelAttribute("khuyenMai") KhuyenMai khuyenMai,
                                BindingResult result,
                                RedirectAttributes attributes) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        if (result.hasErrors()) {
            // In ra lỗi trong log
            result.getAllErrors().forEach(error -> System.out.println("Lỗi: " + error.getDefaultMessage()));

            // Thêm lỗi vào attributes
            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", "Thêm mới khuyến mãi không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/pagination-add-khuyen-mai";
        }
        try {
            KhuyenMai existingKhuyenMai = khuyenMaiService.addKhuyenMai(khuyenMai);
            if (existingKhuyenMai == null) {
                attributes.addFlashAttribute("khuyenMai", khuyenMai);
                attributes.addFlashAttribute("message", "Tên hoặc mã khuyến mãi đã tồn tại.");
                attributes.addFlashAttribute("messageType", "alert-danger");
                attributes.addFlashAttribute("titleMsg", "Thất bại");
                return "redirect:/quan-ly/pagination-add-khuyen-mai";
            }
        } catch (Exception e) {
            // Log lỗi nếu có exception
            System.out.println("Lỗi khi thêm khuyến mãi: " + e.getMessage());
            e.printStackTrace();

            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", "Lỗi hệ thống. Không thể thêm khuyến mãi.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/pagination-add-khuyen-mai";
        }

        attributes.addFlashAttribute("message", "Thêm mới khuyến mãi thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly/pagination-add-khuyen-mai";
    }

    @PostMapping("/update-khuyen-mai/{id}")
    private String updateKhuyenMai(@Valid @ModelAttribute("khuyenMai") KhuyenMai khuyenMai, BindingResult result, RedirectAttributes attributes, @RequestParam("id") Integer idKhuyenMai) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        if (result.hasErrors()) {
            // In ra lỗi trong log
            result.getAllErrors().forEach(error -> System.out.println("Lỗi: " + error.getDefaultMessage()));

            // Thêm lỗi vào attributes
            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", " Cập nhật khuyến mại không thành công");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/pagination-update-khuyen-mai/{id}";
        }
        KhuyenMai updatedKhuyenMai = khuyenMaiService.updateKhuyenMai(khuyenMai, idKhuyenMai);
        if (updatedKhuyenMai == null) {
            attributes.addFlashAttribute("khuyenMai", khuyenMai);
            attributes.addFlashAttribute("message", "Tên khuyến mại đã tồn tại.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/pagination-update-khuyen-mai/{id}";
        }

        attributes.addFlashAttribute("message", "Cập nhật khuyến mại thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly/pagination-update-khuyen-mai/{id}";
    }

    @GetMapping("/delete-khuyen-mai/{id}")
    private String deleteKhuyenMai(@PathVariable("id") Integer idKhuyenMai, RedirectAttributes attributes) {
        khuyenMaiService.deleteKhuyenMai(idKhuyenMai);
        attributes.addFlashAttribute("message", "Đổi trạng thái khuyến mại thành công .");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly/pagination-khuyen-mai";

    }


    @GetMapping("/tim-kiem")
    public String timKiemKhuyenMai(
            @RequestParam(required = false) String maKhuyenMai,
            @RequestParam(required = false) String tenKhuyenMai,
            @RequestParam(value = "kieuKhuyenMai", required = false) String kieuKhuyenMai,
            @RequestParam(required = false) Float mucGiamToiDa,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayBatDau,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKetThuc,
            @PageableDefault(size = 5) Pageable pageable, // Số lượng item trên mỗi trang
            Model model
    ) {
        System.out.println("Tìm kiếm theo mã: " + maKhuyenMai);

        // Chuyển đổi kieuKhuyenMai từ String sang Boolean
        Boolean isVND = null;
        if ("VND".equals(kieuKhuyenMai)) {
            isVND = true;
        } else if ("%".equals(kieuKhuyenMai)) {
            isVND = false;
        }

        // Gọi service tìm kiếm
        Page<KhuyenMai> khuyenMais = khuyenMaiService.timKiemKhuyenMai(
                maKhuyenMai,
                tenKhuyenMai,
                isVND, // Sử dụng isVND đã chuyển đổi
                mucGiamToiDa,
                trangThai,
                ngayBatDau,
                ngayKetThuc,
                pageable
        );

        // Thêm dữ liệu vào model để hiển thị trên giao diện
        model.addAttribute("khuyenMaiList", khuyenMais.getContent()); // Danh sách khuyến mãi
        model.addAttribute("currentPage", pageable.getPageNumber()); // Trang hiện tại
        model.addAttribute("totalPage", khuyenMais.getTotalPages()); // Tổng số trang
        model.addAttribute("khuyenMaiCre", new KhuyenMai()); // Đối tượng mới cho form thêm mới
        model.addAttribute("DataUtils", new DataUtils());

        return "view-admin/dashbroad/khuyen-mai"; // Trả về view danh sách khuyến mãi
    }

}
