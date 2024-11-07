package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.entity.SanPham;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonPdfService;

import javax.swing.JFileChooser;


import java.io.*;
import java.text.NumberFormat;
import java.util.*;

import com.example.kristp.service.HoaDonService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Service
public class HoaDonPdfServiceImpl implements HoaDonPdfService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    BanHangService banHangService;

    @Override
// khi lấy được id hóadđơn
//    public void inHoaDon(Integer idOrder) {
    public ResponseEntity<InputStreamResource> inHoaDon(Integer idOrder) {
        try (PDDocument taiLieu = new PDDocument()) {
            PDPage trang = new PDPage(PDRectangle.A4);
            taiLieu.addPage(trang);

            // Truy xuất hóa đơn từ cơ sở dữ liệu
            HoaDon hoaDon = hoaDonService.findHoaDonById(idOrder);
            Page<HoaDonChiTiet> listHDCT = hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDon, null);

            try (PDPageContentStream luoiNoiDung = new PDPageContentStream(taiLieu, trang)) {
                Integer maHoaDon = hoaDon.getId();
                String tenNhanVien = hoaDon.getNhanVien() == null ? "" : hoaDon.getNhanVien().getTenNhanVien();
                String tenKhachHang = "";
                String sdt = "";
                String ngayTao = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(hoaDon.getNgayTao());

                List<String> sanPhamList = new ArrayList<>();
                List<Integer> soLuongList = new ArrayList<>();
                List<Double> giaBanList = new ArrayList<>();

// Duyệt qua các chi tiết hóa đơn để lấy thông tin sản phẩm, số lượng và giá bán
                for (HoaDonChiTiet hdct : listHDCT) {
                    ChiTietSanPham chiTietSanPham = hdct.getChiTietSanPham();
                    String tenSanPham = chiTietSanPham.getSanPham().getTenSanPham();
                    int soLuong = hdct.getSoLuong();
                    double giaBan = hdct.getGiaTien();

                    sanPhamList.add(tenSanPham);
                    soLuongList.add(soLuong);
                    giaBanList.add(giaBan);
                }
                double tongTien = banHangService.getTongTien(hoaDon);
                String phuongThucThanhToan = "Chuyển khoản";
                String tenKhuyenMai = hoaDon.getKhuyenMai() == null ? "" : hoaDon.getKhuyenMai().getTenKhuyenMai(); // Giảm giá 10%
                double giamGia = hoaDon.getKhuyenMai() == null ? 0 : hoaDon.getKhuyenMai().getGiaTri(); // Giảm giá 10%
                double tongSauGiam = tongTien - giamGia;

                MyTextClass myTextClass = new MyTextClass(taiLieu, luoiNoiDung);
                NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

                try (InputStream fontStream = getClass().getResourceAsStream("/fonts/NotoSans-Rengular.ttf")) {
                    if (fontStream == null) {
                        log.error("Không tìm thấy tệp font!");
                        return ResponseEntity.internalServerError().build();
                    }
                    PDFont font = PDType0Font.load(taiLieu, fontStream);

                    myTextClass.addSingleLineText("KRIST CẢM ƠN", 200, 750, font, 24, Color.BLACK);
                    myTextClass.addSingleLineText("Mã hóa đơn: " + maHoaDon, 50, 710, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tên nhân viên: " + tenNhanVien, 50, 690, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tên khách hàng: " + tenKhachHang, 50, 670, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Số điện thoại: " + sdt, 50, 650, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Ngày tạo: " + ngayTao, 50, 630, font, 14, Color.BLACK);

                    myTextClass.addSingleLineText("Sản phẩm", 50, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Số lượng", 200, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Đơn giá", 300, 600, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tổng tiền", 400, 600, font, 14, Color.BLACK);

                    int yPosition = 580;
                    int rowHeight = 20;

                    for (int i = 0; i < sanPhamList.size(); i++) {
                        myTextClass.addSingleLineText(sanPhamList.get(i), 50, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(String.valueOf(soLuongList.get(i)), 200, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(giaBanList.get(i)), 300, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(soLuongList.get(i) * giaBanList.get(i)), 400, yPosition, font, 14, Color.BLACK);

                        yPosition -= rowHeight;
                    }

                    myTextClass.addSingleLineText("Tổng tiền: " + currencyFormat.format(tongTien) + " VNĐ", 50, yPosition - 20, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tên khuyến mại: " + tenKhuyenMai, 50, yPosition - 40, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Giảm giá: " + currencyFormat.format(giamGia) + " VNĐ", 50, yPosition - 60, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tổng sau giảm: " + currencyFormat.format(tongSauGiam) + " VNĐ", 50, yPosition - 80, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Phương thức thanh toán: " + phuongThucThanhToan, 50, yPosition - 100, font, 14, Color.BLACK);

                } catch (IOException e) {
                    log.error("Lỗi khi tải font: " + e.getMessage());
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            taiLieu.save(outputStream);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=HoaDon_" + hoaDon.getId() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException e) {
            log.error("Lỗi khi tạo hóa đơn PDF: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    // chỉ hiện những hóa đơn có trạng thái là đã thanh toán
    @Override
    public List<HoaDon> findAllHoaDon() {
        return hoaDonRepository.findByTrangThai(HoaDonStatus.DA_THANH_TOAN);
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