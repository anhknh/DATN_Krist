package com.example.kristp.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class apiTest {

    @GetMapping("/test-ca-nhan")
    public Map<String, Object> testNhan() {
        Map<String, Object> response = null ;
            response = new HashMap<>();
            List<String> c = new ArrayList<>(Arrays.asList(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            ));
            response.put("labels", List.of(c.toArray()));

            return  response ;
    }
}
