package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    DataUtils dataUtils;




    @GetMapping("/test")
    public String test(Model model) throws JsonProcessingException {
        Page<HoaDon> hoaDons = hoaDonService.findTop5();
        model.addAttribute("dataUtils" , dataUtils);
        model.addAttribute("hoaDons", hoaDons.getContent());

        Double doanhThuNgay = hoaDonService.doanhThuHomNay();
        System.out.println(doanhThuNgay + "Doanh thu hôm nay");

        Integer soSanPhamBanTrongThang = hoaDonService.soSanPhamBanTrongThang();
        System.out.println(soSanPhamBanTrongThang + "Số sản phẩm bán trong tháng");
        model.addAttribute("doanhThuNgay", doanhThuNgay);
        model.addAttribute("spBanTrongThang", soSanPhamBanTrongThang);
        Double doanhThuNam = hoaDonService.doanhThuTrongNam();
        model.addAttribute("doanhThuNam", doanhThuNam);
        return "view-admin/dashbroad/thong-ke";
    }
}
