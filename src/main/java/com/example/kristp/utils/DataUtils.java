package com.example.kristp.utils;

import com.example.kristp.entity.DanhGia;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.enums.Status;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
            double discountValue = khuyenMai.getGiaTri(); // Giá trị khuyến mại
            double maxDiscount = khuyenMai.getMucGiamToiDa(); // Mức giảm cố hữu (giới hạn)

            if (khuyenMai.getKieuKhuyenMai()) {
                // Giảm theo phần trăm
                if (discountValue > 0 && discountValue <= 100) {
                    double percentDiscount = finalAmount * discountValue / 100;
                    // Giảm theo phần trăm nhưng không vượt quá mức giảm cố hữu
                    finalAmount -= Math.min(percentDiscount, maxDiscount);
                }
            } else {
                // Giảm theo số tiền cố định
                finalAmount -= Math.min(discountValue, maxDiscount); // Không giảm quá mức giảm cố hữu
            }
        }

        // Luôn thêm phí vận chuyển
        finalAmount += phiVanChuyen;

        // Đảm bảo tổng tiền không âm
        if (finalAmount < 0) {
            finalAmount = 0;
        }

        return formatCurrency(finalAmount);
    }




    public static double calculatorTotal2(double tongTien, KhuyenMai khuyenMai, Float phiVanChuyen) {
        double finalAmount = tongTien; // Tổng tiền ban đầu

        if (khuyenMai != null && khuyenMai.getKieuKhuyenMai() != null) {
            double discountValue = khuyenMai.getGiaTri(); // Giá trị khuyến mại
            double maxDiscount = khuyenMai.getMucGiamToiDa(); // Mức giảm cố hữu (giới hạn)

            if (khuyenMai.getKieuKhuyenMai()) {
                // Giảm theo phần trăm
                if (discountValue > 0 && discountValue <= 100) {
                    double percentDiscount = finalAmount * discountValue / 100;
                    // Giảm theo phần trăm nhưng không vượt quá mức giảm cố hữu
                    finalAmount -= Math.min(percentDiscount, maxDiscount);
                }
            } else {
                // Giảm theo số tiền cố định
                finalAmount -= Math.min(discountValue, maxDiscount); // Không giảm quá mức giảm cố hữu
            }
        }

        // Luôn thêm phí vận chuyển
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

    public static String formatStatus(String trangThai) {
        if (trangThai.equals("CHO_XAC_NHAN")) {
            return "Chờ xác nhận";
        } else if (trangThai.equals("DANG_XU_LY")) {
            return "Đang xử lý";
        } else if (trangThai.equals("DANG_GIAO_HANG")) {
            return "Đang giao hàng";
        } else if (trangThai.equals("HOAN_TAT")) {
            return "Hoàn tất";
        } else if (trangThai.equals("HOA_DON_CHO")) {
            return "Hóa đơn chờ";
        } else if (trangThai.equals("DA_THANH_TOAN")) {
            return "Đã thanh toán";
        } else if (trangThai.equals("CHUA_THANH_TOAN")) {
            return "Chưa thanh toán";
        }
        else if (trangThai.equals("DA_HUY")) {
            return "Đã Hủy";
        }
        else if (trangThai.equals("online")) {
            return "VN Pay";
        }
        else if (trangThai.equals("offline")) {
            return "Trực tiếp";
        } else {
            return "Trạng thái không xác định";
        }
    }

    public String getCssClass(String trangThai) {
        switch (trangThai) {
            case "CHO_XAC_NHAN", "HOA_DON_CHO":
                return "pending-confirmation";
            case "DANG_XU_LY":
                return "in-processing";
            case "DANG_GIAO_HANG":
                return "shipping";
            case "HOAN_TAT", "DA_THANH_TOAN":
                return "completed";
            case "CHUA_THANH_TOAN", "DA_HUY":
                return "cancelled";
            default:
                return "";
        }
    }

    /**
     * Hàm tính tổng điểm đánh giá
     *
     * @param danhGiaList Danh sách các đối tượng đánh giá
     * @return Tổng điểm đánh giá đã làm tròn (nếu không có đánh giá hợp lệ trả về 0)
     */
    public static float tinhTongDiemDanhGia(List<DanhGia> danhGiaList) {
        if (danhGiaList == null || danhGiaList.isEmpty()) {
            return 0;
        }

        // Tính tổng điểm
        int tongDiem = danhGiaList.stream()
                .filter(danhGia -> danhGia != null && danhGia.getMucDoDanhGia() != null) // Lọc bỏ các giá trị null
                .mapToInt(DanhGia::getMucDoDanhGia) // Lấy điểm đánh giá
                .sum();

        // Đảm bảo phép chia trả về giá trị kiểu float (hoặc double)
        float totalPointReview = (float) tongDiem / danhGiaList.size();

// Làm tròn đến 1 chữ số sau dấu thập phân
        BigDecimal rounded = new BigDecimal(totalPointReview).setScale(1, RoundingMode.HALF_UP);

// Chuyển đổi lại về kiểu float
        totalPointReview = rounded.floatValue();

        return totalPointReview; // Trả về tổng điểm
    }

    public static String generateBarcode(int length) {
        if (length <= 0) {
            return null;
        }

        // Chuỗi chứa các ký tự hợp lệ cho barcode (chữ hoa và số)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder barcode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            // Lấy ký tự ngẫu nhiên từ chuỗi characters
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            barcode.append(randomChar);
        }

        return barcode.toString();
    }

}
