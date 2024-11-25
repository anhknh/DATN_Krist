package com.example.kristp.utils;

import com.example.kristp.entity.KhuyenMai;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class DataUtils {

    public static String formatCurrency(double amount) {
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return currencyFormat.format(amount);
    }

    public static String calculatorTotal(double tongTien, KhuyenMai khuyenMai, Float phiVanChuyen) {
        double finalAmount = tongTien; // Tổng tiền ban đầu

        if (khuyenMai != null && khuyenMai.getKieuKhuyenMai() != null) {
            double discountValue = khuyenMai.getGiaTri();

            if (khuyenMai.getKieuKhuyenMai()) {
                // Giảm theo phần trăm
                if (discountValue > 0 && discountValue <= 100) {
                    finalAmount -= (finalAmount * discountValue / 100);
                }

            } else  {
                // Giảm theo số tiền cố định
                finalAmount -= discountValue;
            }
        }

        // Luôn thêm phí vận chuyển 30,000
        finalAmount += phiVanChuyen;

        // Đảm bảo tổng tiền không âm
        if (finalAmount < 0) {
            finalAmount = 0;
        }

        return formatCurrency(finalAmount);
    }



    public static double calculatorTotal2(double tongTien, KhuyenMai khuyenMai , Float phiVanChuyen) {
        double finalAmount = tongTien; // Tổng tiền ban đầu

        if (khuyenMai != null && khuyenMai.getKieuKhuyenMai() != null) {
            double discountValue = khuyenMai.getGiaTri();

            if (khuyenMai.getKieuKhuyenMai()) {
                // Giảm theo phần trăm
                if (discountValue > 0 && discountValue <= 100) {
                    finalAmount -= (finalAmount * discountValue / 100);
                }

            } else  {
                // Giảm theo số tiền cố định
                finalAmount -= discountValue;
            }
        }

        // Luôn thêm phí vận chuyển 30,000
        finalAmount += phiVanChuyen;

        // Đảm bảo tổng tiền không âm
        return Math.max(finalAmount, 0);
    }


    public static String formatFullAddress(String diaChi, String phuongXa, String quanHuyen, String tinhThanh) {
        // Tạo danh sách các phần của địa chỉ
        List<String> parts = Arrays.asList(diaChi, phuongXa, quanHuyen, tinhThanh);

        // Loại bỏ phần tử null hoặc chuỗi rỗng
        List<String> filteredParts = parts.stream()
                .filter(part -> part != null && !part.trim().isEmpty()) // Loại bỏ null hoặc chuỗi rỗng
                .map(String::trim) // Xóa khoảng trắng thừa
                .collect(Collectors.toList());

        // Nối các phần tử lại với dấu phẩy
        return String.join(", ", filteredParts);
    }

}
