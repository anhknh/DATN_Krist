package com.example.kristp.utils;

import com.example.kristp.entity.KhachHang;
import com.example.kristp.entity.NhanVien;
import com.example.kristp.repository.NhanVienRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authen {



    public static NhanVien nhanVien = null;
    public  static KhachHang khachHang = null;

    public static void clearNhanVien() {
        nhanVien = null;
    }
    public static void clearKhachHang() {
        khachHang = null;
    }


    public static boolean isLoginKhachHang(HttpSession session) {
        khachHang = (KhachHang) session.getAttribute("KhachHang");
        return khachHang != null;
    }
    public static boolean isLoginNhanVien(HttpSession session) {
        nhanVien = (NhanVien)  session.getAttribute("");
        return nhanVien != null;
    }
//
    public static String getRoleKhachHang(HttpSession session) {
        if (isLoginKhachHang(session)) {
            return khachHang.getTaiKhoan().getChucVu();
        } else {
            return null; // or handle as needed if not logged in
        }
    }
    public static String getRoleNhanVien(HttpSession session) {
        if (isLoginNhanVien(session)) {
            return nhanVien.getTaiKhoan().getChucVu();
        } else {
            return null; // or handle as needed if not logged in
        }
    }
}
