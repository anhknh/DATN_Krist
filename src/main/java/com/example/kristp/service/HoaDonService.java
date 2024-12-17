package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HoaDonService {

    Boolean taoHoaDon();
    Boolean thanhToanHoaDon(HoaDon hoaDon);
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);
    Page<HoaDon> getAllHoaDon(String trangThai, Pageable pageable);
    Page<HoaDon> timKiemHoaDon(String id, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, Pageable pageable);
    Integer getCountByTrangThai(HoaDonStatus hoaDonStatus);

    Page<HoaDon> getPaginationHoaDon(Integer pageNo , HoaDonStatus trangThai , Integer idKhachHang );


    Page<HoaDon> findTop5();


    List<String> doanhThuTungThang(Integer year);

    Double doanhThuHomNay();

    Integer soSanPhamBanTrongThang();

    Double doanhThuTrongNam();

    List<Integer> trangThaiDonHang();

    boolean changeStatus(Integer id);
    boolean changePhiVanChuyen(Integer id, double phiVanChuyen);

    List<Integer> getNamCoTrongHoaDon();


    boolean xoaSanPham(Integer idHoaDon);
    boolean capNhatSoLuong(Integer idHoaDonChiTiet, int soLuongMoi);
    boolean themSanPhamDon(Integer idHoaDon, Integer idChiTiet);
}
