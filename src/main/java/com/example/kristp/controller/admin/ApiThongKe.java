package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class ApiThongKe {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/api/chart-data")
    public Map<String, Object> getChartData(@RequestParam(value = "a" , defaultValue = "1")Integer a) {

        Map<String, Object> response = null ;
            System.out.println("Hello Hello");
            response = new HashMap<>();
//            Biểu đồ cột
//            Labels hiển thị bên dưới
            List<String> c = new ArrayList<>(Arrays.asList(
                    "1", "2", "3", "4", "5", "6",
                    "7", "8", "9", "10", "11", "12"
            ));
            response.put("labels", List.of(c.toArray()));
//            Dữ liệu đổ vào trên bảng
            List<String> data = hoaDonService.doanhThuTungThang();
            response.put("dataValues", data.toArray());
            Random random = new Random();
            List<String> background = new ArrayList<>();
            for (int i = 0 ; i < c.size() ; i++){
                background.add("rgba("+(random.nextInt(255) + 1)+","+ (random.nextInt(255) + 1)+","+(random.nextInt(255) + 1)+", 0.2)");
            }
            response.put("backgroundColors", List.of(background.toArray()
            ));
            List<String> border = new ArrayList<>();
            for (int i = 0 ; i < c.size() ; i++){
                border.add("rgba("+(random.nextInt(255) + 1)+","+ (random.nextInt(255) + 1)+","+(random.nextInt(255) + 1)+", 1)");
            }
            response.put("borderColors", List.of(
                    border.toArray()
            ));
            // Bảng hiển thị top 5
            Page<HoaDon> hoaDons = hoaDonService.findTop5();

            System.out.println(hoaDons.getContent() + "Đã lấy được dữ liệu");
            response.put("hoaDons", hoaDons.getContent().toArray());
//            Biểu đồ tròn
            List<String> cTron = new ArrayList<>(Arrays.asList(
                    "Chờ xác nhận" , "Chưa thanh toán" , "Đã thanh toán" , "Đang giao hàng" , "Đang xử lý" ,
                    "Hóa đơn chờ" , "Hoàn tất"
            ));
            response.put("labelsTron", List.of(cTron.toArray()));
            List<Integer> listTrangThai = hoaDonService.trangThaiDonHang();
            response.put("dataValuesTron", listTrangThai.toArray());
            List<String> backgroundTron = new ArrayList<>();
            for (int i = 0 ; i < cTron.size() ; i++){
                backgroundTron.add("rgba("+(random.nextInt(255) + 1)+","+ (random.nextInt(255) + 1)+","+(random.nextInt(255) + 1)+", 0.2)");
            }
            response.put("backgroundColorsTron", List.of(backgroundTron.toArray()
            ));

        return response;
    }
}
