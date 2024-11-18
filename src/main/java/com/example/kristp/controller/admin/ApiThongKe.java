package com.example.kristp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class ApiThongKe {
    @GetMapping("/api/chart-data")
    public Map<String, Object> getChartData(@RequestParam("a")Integer a) {
        System.out.println("Đã vào đây");
        Map<String, Object> response = null ;
        if(a == 1){
            response = new HashMap<>();
            List<String> c = new ArrayList<>(Arrays.asList(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            ));
            response.put("labels", List.of(c.toArray()));
            response.put("dataValues", List.of(65+a, 59+a, 80+a, 81+a, 56+a, 55+a));
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

            List<String> cTron = new ArrayList<>(Arrays.asList(
                    "Đen" , "Đỏ" , "Trắng" , "Vàng" , "Cam"
            ));
            response.put("labelsTron", List.of(cTron.toArray()));
            response.put("dataValuesTron", List.of(65+a, 59+a, 80+a, 200+a, 56+a));
            List<String> backgroundTron = new ArrayList<>();
            for (int i = 0 ; i < cTron.size() ; i++){
                backgroundTron.add("rgba("+(random.nextInt(255) + 1)+","+ (random.nextInt(255) + 1)+","+(random.nextInt(255) + 1)+", 0.2)");
            }
            response.put("backgroundColorsTron", List.of(backgroundTron.toArray()
            ));
            response.put("totalorder", "1");
            response.put("comments", "3");
            response.put("totalrevenue", "6");
        }
        else if(a == 2){
            response = new HashMap<>();
            List<String> c = new ArrayList<>(Arrays.asList(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            ));
            response.put("labels", List.of(c.toArray()));
            response.put("dataValues", List.of(65+a, 59+a, 80+a, 81+a, 56+a, 55+a));
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

            List<String> cTron = new ArrayList<>(Arrays.asList(
                    "Đen" , "Đỏ" , "Trắng" , "Vàng" , "Cam"
            ));
            response.put("labelsTron", List.of(cTron.toArray()));
            response.put("dataValuesTron", List.of(65+a, 59+a, 80+a, 200+a, 56+a));
            List<String> backgroundTron = new ArrayList<>();
            for (int i = 0 ; i < cTron.size() ; i++){
                backgroundTron.add("rgba("+(random.nextInt(255) + 1)+","+ (random.nextInt(255) + 1)+","+(random.nextInt(255) + 1)+", 0.2)");
            }
            response.put("backgroundColorsTron", List.of(backgroundTron.toArray()
            ));
            response.put("totalorder", "1");
            response.put("comments", "3");
            response.put("totalrevenue", "6");
        }
        return response;
    }
}
