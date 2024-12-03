package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.GioHangChiTietRepo;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DatHangServiceImpl implements DatHangService {

    @Autowired
    HoaDonService hoaDonServicel;
    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    KhuyenMaiService  khuyenMaiService;
    @Autowired
    DiaChiService diaChiService;
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DataUtils dataUtils;


    @Override
    public boolean datHangOnline(List<GioHangChiTiet> gioHangChiTietList, Integer idDiaChi,
                                 Integer idKhuyenMai, String phuongThucThanhToan, Float tongTien, Float phiVanChuyen) {
        DiaChi diaChi = diaChiService.getDiaChiById(idDiaChi);
        KhuyenMai khuyenMai = null;
        if(idKhuyenMai != null) {
            khuyenMai = khuyenMaiService.getKhuyenMaiById(idKhuyenMai);
        }
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(dataUtils.generateBarcode(8));
        hoaDon.setTrangThai(HoaDonStatus.CHO_XAC_NHAN);
        hoaDon.setKhuyenMai(khuyenMai);
        hoaDon.setDiaChi(diaChi);
        hoaDon.setHinhThucThanhToan(phuongThucThanhToan);
        hoaDon.setNgayDatHang(new Date());
        hoaDon.setTongTien(BigDecimal.valueOf(tongTien));
        hoaDon.setPhiVanChuyen(phiVanChuyen);
        hoaDon.setKhachHang(Authen.khachHang);
        if(phuongThucThanhToan.equals("online")) {
            hoaDon.setTrangThaiThanhToan(HoaDonStatus.DA_THANH_TOAN);
        } else {
            hoaDon.setTrangThaiThanhToan(HoaDonStatus.CHUA_THANH_TOAN);
        }
        HoaDon hoaSonSaved = hoaDonRepository.save(hoaDon);
        //lưu chi tiết hóa đơn
        for (GioHangChiTiet gioHang: gioHangChiTietList) {

            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setChiTietSanPham(gioHang.getChiTietSanPham());
            hoaDonChiTiet.setSoLuong(gioHang.getSoLuong());
            hoaDonChiTiet.setGiaTien(gioHang.getChiTietSanPham().getDonGia());
            hoaDonChiTiet.setHoaDon(hoaSonSaved);
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            //giảm số lượng sản phẩm
            // Giảm số lượng sản phẩm
            ChiTietSanPham chiTietSanPham = gioHang.getChiTietSanPham();
            int soLuongHienTai = chiTietSanPham.getSoLuong();
            int soLuongGiam = gioHang.getSoLuong();

            // Kiểm tra số lượng trước khi trừ
            if (soLuongHienTai < soLuongGiam) {
                return false;
            }

            // Giảm số lượng sản phẩm
            chiTietSanPham.setSoLuong(soLuongHienTai - soLuongGiam);
            chiTietSanPhamRepository.save(chiTietSanPham); // Lưu lại thay đổi
        }
        gioHangChiTietRepo.deleteAll(gioHangChiTietList);


        return true;
    }
}
