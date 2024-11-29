package com.example.kristp.service;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HoaDonService {

    Boolean taoHoaDon();
    Boolean thanhToanHoaDon(HoaDon hoaDon);
    List<HoaDon> findAllHoaDonCho();
    HoaDon findHoaDonById(Integer id);
    List<HoaDon> getAllHoaDon(String trangThai);
    List<HoaDon> timKiemHoaDon(Integer id, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc);
    Integer getCountByTrangThai(HoaDonStatus hoaDonStatus);

    Page<HoaDon> getPaginationHoaDon(Integer pageNo , HoaDonStatus trangThai , Integer idKhachHang );


    Page<HoaDon> findTop5();


    List<String> doanhThuTungThang(Integer year);

    Double doanhThuHomNay();

    Integer soSanPhamBanTrongThang();

    Double doanhThuTrongNam();

    List<Integer> trangThaiDonHang();

    boolean changeStatus(Integer id);

    List<Integer> getNamCoTrongHoaDon();
}
