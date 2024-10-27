package com.example.kristp.utils;

import com.example.kristp.entity.NhanVien;
import com.example.kristp.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authen {



    public static NhanVien nhanVien = null;

    public static void clear() {
        nhanVien = null;
    }


//    public static boolean isLogin(HttpSession session) {
//        customer = (Customer) session.getAttribute("customer");
//        return customer != null;
//    }
//
//    public static String getRole(HttpSession session) {
//        if (isLogin(session)) {
//            return customer.getUserId().getRole();
//        } else {
//            return null; // or handle as needed if not logged in
//        }
//    }
}
