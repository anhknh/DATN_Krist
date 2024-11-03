package com.example.kristp.controller.admin;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.service.HoaDonPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.util.List;


@Controller
@RequestMapping("/hoa-don-pdf/")
public class HoaDonPdfController {

    @Autowired
    private HoaDonPdfService hoaDonPdfService;

    //Bảng hóa đơn đã thanh toán
    @GetMapping("/in-hoa-don")
        public String showHoaDonList(Model model) {
            List<HoaDon> hoaDonList = hoaDonPdfService.findAllHoaDon();
            model.addAttribute("hoaDonList", hoaDonList);
        return "view-admin/dashbroad/in-hoa-don"; // Trả về trang view
    }

    //In Hóa Đơn
    @GetMapping("/in/{id}")
    public ResponseEntity<InputStreamResource> printHoaDon(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        return hoaDonPdfService.inHoaDon(id);
    }


}

