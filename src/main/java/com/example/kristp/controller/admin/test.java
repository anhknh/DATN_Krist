package com.example.kristp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class test {
    @GetMapping("/test")
    public String test(Model model) throws JsonProcessingException {
        // Fake data for demonstration
        List<String> labels = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        List<Integer> data = Arrays.asList(1, 1, 1,3 , 1, 1, 1,1,1,1,1,2);

        // Mã hóa dữ liệu thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String labelsJson = objectMapper.writeValueAsString(labels);
        String dataJson = objectMapper.writeValueAsString(data);
        model.addAttribute("labels", labelsJson);
        model.addAttribute("data", dataJson);

        List<String> labels1 = Arrays.asList(labels.get(0), "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        List<Integer> data1 = Arrays.asList(1, 1, 1,3 , 1, 1, 1,1,1,1,1,2);
        String labelsJson2 = objectMapper.writeValueAsString(labels1);
        String dataJson2 = objectMapper.writeValueAsString(data1);
        model.addAttribute("labels2", labelsJson2);
        model.addAttribute("data2", dataJson2);

        List<String> backgroundColors = Arrays.asList(
                "rgba(250, 250, 250, 1)",   // Xám nhạt nhất, gần trắng hoàn toàn
                "rgba(245, 245, 245, 1)",   // Xám gần trắng
                "rgba(238, 238, 238, 1)",   // Xám rất nhạt
                "rgba(221, 221, 221, 1)",   // Xám sáng hơn
                "rgba(204, 204, 204, 1)",   // Xám sáng
                "rgba(187, 187, 187, 1)",   // Xám nhạt hơn
                "rgba(170, 170, 170, 1)",   // Xám nhạt
                "rgba(136, 136, 136, 1)",   // Xám vừa
                "rgba(102, 102, 102, 1)",   // Xám trung bình
                "rgba(68, 68, 68, 1)",      // Xám đậm
                "rgba(34, 34, 34, 1)",      // Đen nhạt
                "rgba(0, 0, 0, 1)"          // Đen
        );
        String backgroundColorJson = objectMapper.writeValueAsString(backgroundColors);
        model.addAttribute("backgroundColor", backgroundColorJson);
        List<String> borderColors = Arrays.asList(
                "rgb(255, 99, 132)",
                "rgb(255, 159, 64)",
                "rgb(255, 205, 86)",
                "rgb(75, 192, 192)",
                "rgb(54, 162, 235)",
                "rgb(153, 102, 255)",
                "rgb(201, 203, 207)",
                "rgb(201, 203, 207)",
                "rgb(201, 203, 207)",
                "rgb(201, 203, 207)",
                "rgb(201, 203, 207)",
                "rgb(201, 203, 207)"
        );
        String borderColorJson = objectMapper.writeValueAsString(borderColors);
        model.addAttribute("borderColor", borderColorJson);


//        Lấy các dữ liệu ra để hiển thị vào các ô
        model.addAttribute("totalorder" , 5);
        model.addAttribute("comments" , 5);
        model.addAttribute("totalrevenue" , 5);
        return "view-admin/dashbroad/thong-ke";
    }
}
