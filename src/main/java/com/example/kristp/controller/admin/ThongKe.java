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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly")
public class ThongKe {
    @Autowired
    private HoaDonService hoaDonService ;
    @GetMapping("/thong-ke")
    public String test(Model model) throws JsonProcessingException {
        Page<HoaDon> hoaDons = hoaDonService.findTop5();
        model.addAttribute("dataUtils" , new DataUtils());
        model.addAttribute("hoaDons", hoaDons.getContent());

        Double doanhThuNgay = hoaDonService.doanhThuHomNay();
        if(doanhThuNgay == null) doanhThuNgay = 0.0;
        System.out.println(doanhThuNgay + "Doanh thu hôm nay");
        Integer soSanPhamBanTrongThang = hoaDonService.soSanPhamBanTrongThang();
        if(soSanPhamBanTrongThang == null) soSanPhamBanTrongThang = 0;
        System.out.println(soSanPhamBanTrongThang + "Số sản phẩm bán trong tháng");
        model.addAttribute("doanhThuNgay", doanhThuNgay);
        model.addAttribute("spBanTrongThang", soSanPhamBanTrongThang);
        Double doanhThuNam = hoaDonService.doanhThuTrongNam();
        if(doanhThuNam == null) doanhThuNam = 0.0;
        model.addAttribute("doanhThuNam", doanhThuNam);
        return "view-admin/dashbroad/thong-ke";
    }
}
