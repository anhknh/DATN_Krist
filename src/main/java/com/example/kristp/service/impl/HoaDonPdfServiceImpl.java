package com.example.kristp.service.impl;

import com.example.kristp.service.HoaDonPdfService;
import java.text.NumberFormat;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class HoaDonPdfServiceImpl implements HoaDonPdfService {
    @Override
    public void inHoaDon() {
        try (PDDocument taiLieu = new PDDocument()) {
            PDPage trang = new PDPage(PDRectangle.A4);
            taiLieu.addPage(trang);

            // Thêm nội dung vào trang
            try (PDPageContentStream luoiNoiDung = new PDPageContentStream(taiLieu, trang)) {
                // Fake data
                String maHoaDon = "HD123456";
                String tenNhanVien = "Đoàn Hiếu";
                String tenKhachHang = "Đỗ Nghèo Khỉ";
                String sdt = "0986465398";
                String ngayTao = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                String[] sanPham = {"San pham 1", "San pham 2"};
                int[] soLuong = {2, 1};
                double[] giaBan = {100000, 150000};
                double tongTien = (soLuong[0] * giaBan[0]) + (soLuong[1] * giaBan[1]);
                String phuongThucThanhToan = "Tiền mặt";
                double giamGia = 0.1 * tongTien; // Giảm giá 10%
                double tongSauGiam = tongTien - giamGia;

                MyTextClass myTextClass = new MyTextClass(taiLieu, luoiNoiDung);
                NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

                // Kiểm tra font
                try (InputStream fontStream = getClass().getResourceAsStream("/fonts/NotoSans-Rengular.ttf")) {
                    if (fontStream == null) {
                        log.error("Không tìm thấy tệp font!");
                        return;
                    }
                    PDFont font = PDType0Font.load(taiLieu, fontStream);


                    // Tiêu đề
                    myTextClass.addSingleLineText("KRIST CẢM ƠN", 200, 750, font, 24, Color.BLACK);

                    // Thêm thông tin hóa đơn
                    myTextClass.addSingleLineText("Mã hóa đơn: " + maHoaDon, 50, 710, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tên nhân viên: " + tenNhanVien, 50, 690, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tên khách hàng: " + tenKhachHang, 50, 670, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Số điện thoại: " + sdt, 50, 650, font, 14, Color.BLACK); // Thêm số điện thoại
                    myTextClass.addSingleLineText("Ngày tạo: " + ngayTao, 50, 630, font, 14, Color.BLACK);

                    // Thêm tiêu đề bảng sản phẩm
                    myTextClass.addSingleLineText("Sản phẩm", 50, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Số lượng", 200, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Đơn giá", 300, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tổng tiền", 400, 600, font, 14, Color.BLACK);

                    int yPosition = 580; // Vị trí bắt đầu
                    int rowHeight = 20;  // Chiều cao mỗi hàng

                    for (int i = 0; i < sanPham.length; i++) {
                        myTextClass.addSingleLineText(sanPham[i], 50, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(String.valueOf(soLuong[i]), 200, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(giaBan[i]) , 300, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(soLuong[i] * giaBan[i]), 400, yPosition, font, 14, Color.BLACK);

                        yPosition -= rowHeight; // Giảm vị trí y cho mỗi dòng
                    }

                    // Thêm thông tin tổng kết
                    myTextClass.addSingleLineText("Tổng tiền: " + currencyFormat.format(tongTien) + " VNĐ", 50, yPosition - 20, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Giảm giá: " + currencyFormat.format(giamGia) + " VNĐ", 50, yPosition - 40, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tổng sau giảm: " + currencyFormat.format(tongSauGiam) + " VNĐ", 50, yPosition - 60, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Phương thức thanh toán: " + phuongThucThanhToan, 50, yPosition - 80, font, 14, Color.BLACK);
                } catch (IOException e) {
                    log.error("Lỗi khi tải font: " + e.getMessage());
                }
            } // Kết thúc luồng nội dung trước khi lưu tài liệu

            // Lưu file PDF vào đường dẫn
            taiLieu.save("E:\\KHOI SU DOANH NGHIEP\\hoa_don.pdf"); // Đường dẫn và tên file
            System.out.println("HÓA ĐƠN ĐÃ ĐƯỢC TẠO");

        } catch (IOException e) {
            log.error("Lỗi khi tạo hóa đơn PDF: " + e.getMessage());
        }
    }

    private void drawTableBorder(PDPageContentStream contentStream, float x, float y, float width, float height) throws IOException {
        contentStream.moveTo(x, y);
        contentStream.lineTo(x + width, y);
        contentStream.lineTo(x + width, y - height);
        contentStream.lineTo(x, y - height);
        contentStream.closePath();
        contentStream.stroke();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MyTextClass {
        PDDocument taiLieu;
        PDPageContentStream luoiNoiDung;

        void addSingleLineText(
                String text,
                int xPosition,
                int yPosition,
                PDFont font,
                float fontSize,
                Color color
        ) throws IOException {
            luoiNoiDung.beginText();
            luoiNoiDung.setFont(font, fontSize);
            luoiNoiDung.setNonStrokingColor(color);
            luoiNoiDung.newLineAtOffset(xPosition, yPosition);
            luoiNoiDung.showText(text);
            luoiNoiDung.endText();
        }
    }
}