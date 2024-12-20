package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonPdfService;

import javax.swing.JFileChooser;


import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.DataUtils;
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
    @Autowired
    DataUtils dataUtils;

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
                String maHoaDon = hoaDon.getMaHoaDon();
                String phiVanChuyen = dataUtils.formatCurrency(hoaDon.getPhiVanChuyen());
                String tenNhanVien = hoaDon.getNhanVien() == null ? "" : hoaDon.getNhanVien().getTenNhanVien();
                String tenKhachHang;
                String sdt;
                if (hoaDon.getDiaChi() != null) {
                    tenKhachHang = hoaDon.getDiaChi().getTenKhachHang();

                    sdt = hoaDon.getDiaChi().getSdt();
                } else {
                    KhachHang khachHang = hoaDon.getKhachHang();
                    tenKhachHang = (khachHang != null && khachHang.getTenKhachHang() != null && !khachHang.getTenKhachHang().isBlank())
                            ? khachHang.getTenKhachHang()
                            : "Khách vãng lai";

                    sdt = (khachHang != null && khachHang.getSoDienThoai() != null && !khachHang.getSoDienThoai().isBlank())
                            ? khachHang.getSoDienThoai()
                            : "N/A";
                }
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
                BigDecimal tongTien2 = hoaDon.getTongTien();
                double tongTienDouble = tongTien2 != null ? tongTien2.doubleValue() : 0.0;
                String tenKhuyenMai = (hoaDon.getKhuyenMai() == null || hoaDon.getKhuyenMai().getTenKhuyenMai() == null || hoaDon.getKhuyenMai().getTenKhuyenMai().isBlank())
                        ? "Không"
                        : hoaDon.getKhuyenMai().getTenKhuyenMai();
                double giamGia = hoaDon.getKhuyenMai() == null ? 0 : hoaDon.getKhuyenMai().getGiaTri(); // Giảm giá 10%
                double giamGiaToiDa = hoaDon.getKhuyenMai() == null ? 0 : hoaDon.getKhuyenMai().getMucGiamToiDa(); // Giảm giá 10%

                double tongSauGiam = dataUtils.calculatorTotal2(tongTienDouble, hoaDon.getKhuyenMai(), hoaDon.getPhiVanChuyen());
                String phuongThucThanhToan = hoaDon.getHinhThucThanhToan().equals("online") ? "VNPay" : "Khi nhận hàng";

                MyTextClass myTextClass = new MyTextClass(taiLieu, luoiNoiDung);
                NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

                try (InputStream fontStream = getClass().getResourceAsStream("/fonts/NotoSans-Rengular.ttf")) {
                    if (fontStream == null) {
                        log.error("Không tìm thấy tệp font!");
                        return ResponseEntity.internalServerError().build();
                    }
                    PDFont font = PDType0Font.load(taiLieu, fontStream);

                    int yPosition = 750; // Điểm bắt đầu in văn bản
                    int rowHeight = 20;  // Khoảng cách giữa các dòng

// Thông tin tiêu đề
                    myTextClass.addSingleLineText("KRIST CẢM ƠN", 200, yPosition, font, 24, Color.BLACK);
                    yPosition -= rowHeight * 2;

                    myTextClass.addSingleLineText("Mã hóa đơn: " + maHoaDon, 50, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Tên nhân viên: " + tenNhanVien, 50, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Tên khách hàng: " + tenKhachHang, 50, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Số điện thoại: " + sdt, 50, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Ngày tạo: " + ngayTao, 50, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight * 2;

// In tiêu đề cột
                    myTextClass.addSingleLineText("Sản phẩm", 50, yPosition, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Số lượng", 200, yPosition, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Đơn giá", 300, yPosition, font, 14, Color.BLACK);
                    myTextClass.addSingleLineText("Tổng tiền", 400, yPosition, font, 14, Color.BLACK);
                    yPosition -= rowHeight;

// In danh sách sản phẩm
                    for (int i = 0; i < sanPhamList.size(); i++) {
                        myTextClass.addSingleLineText(sanPhamList.get(i), 50, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(String.valueOf(soLuongList.get(i)), 200, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(giaBanList.get(i)), 300, yPosition, font, 14, Color.BLACK);
                        myTextClass.addSingleLineText(currencyFormat.format(soLuongList.get(i) * giaBanList.get(i)), 400, yPosition, font, 14, Color.BLACK);
                        yPosition -= rowHeight;
                    }

// Tổng tiền trước giảm giá
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Tổng tiền: " + currencyFormat.format(tongTien) + " VNĐ", 50, yPosition, font, 14, Color.BLACK);

// Phí giao hàng
                    yPosition -= rowHeight;
                    myTextClass.addSingleLineText("Phí giao hàng: " + phiVanChuyen + " VNĐ", 50, yPosition, font, 14, Color.BLACK);

// Khuyến mãi
                    if (hoaDon.getKhuyenMai() != null) {
                        yPosition -= rowHeight;
                        myTextClass.addSingleLineText("Tên khuyến mại: " + tenKhuyenMai, 50, yPosition, font, 14, Color.BLACK);

                        yPosition -= rowHeight;
                        if (hoaDon.getKhuyenMai().getKieuKhuyenMai()) {
                            myTextClass.addSingleLineText("Giảm giá: " + currencyFormat.format(giamGia) + " %", 50, yPosition, font, 14, Color.BLACK);
                            yPosition -= rowHeight;
                            myTextClass.addSingleLineText("Tối đa: " + currencyFormat.format(giamGiaToiDa) + " VNĐ", 50, yPosition, font, 14, Color.BLACK);
                        } else {
                            myTextClass.addSingleLineText("Giảm giá: " + currencyFormat.format(giamGia) + " VNĐ", 50, yPosition, font, 14, Color.BLACK);
                        }
                    }

// Tổng tiền sau giảm giá (đã cộng phí giao hàng)
                    yPosition -= rowHeight * 2;
                    myTextClass.addSingleLineText("Tổng sau giảm: " + currencyFormat.format(tongSauGiam) + " VNĐ", 50, yPosition, font, 14, Color.BLACK);

// Phương thức thanh toán
                    yPosition -= rowHeight * 2;
                    if (hoaDon.getDiaChi() == null) {
                        myTextClass.addSingleLineText("Phương thức thanh toán: Tại quầy", 50, yPosition, font, 14, Color.BLACK);
                    } else {
                        myTextClass.addSingleLineText("Phương thức thanh toán: " + phuongThucThanhToan, 50, yPosition, font, 14, Color.BLACK);
                    }


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