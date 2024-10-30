package com.example.kristp.controller.admin;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.service.HoaDonPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;


@Controller
@RequestMapping("/hoa-don-pdf/")
public class HoaDonPdfController {

    @Autowired
    private HoaDonPdfService hoaDonPdfService;

    // Trang để in hóa đơn
    @GetMapping("/in-hoa-don")
    public String showInvoicePage(Model model) {
        model.addAttribute("message", "Nhấn nút bên dưới để in hóa đơn.");
        return "view-admin/dashbroad/in-hoa-don"; // Trả về trang view
    }

    // Hàm in hóa đơn
    @PostMapping("/in")
    public ResponseEntity<String> printInvoice() {
        try {
            hoaDonPdfService.inHoaDon(); // Gọi phương thức để in hóa đơn
            return new ResponseEntity<>("Hóa đơn đã được in thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi in hóa đơn: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

