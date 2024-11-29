package com.example.kristp.service.impl;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.Authen;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    BanHangService banHangService;

    @Override
    public Boolean taoHoaDon() {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNhanVien(nhanVienRepository.findById(1).orElse(null));
        hoaDon.setTrangThai(HoaDonStatus.HOA_DON_CHO);
        hoaDon.setTrangThaiThanhToan(HoaDonStatus.CHUA_THANH_TOAN);
        if (hoaDonRepository.countByTrangThai(HoaDonStatus.HOA_DON_CHO) < 5) {
            hoaDonRepository.save(hoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean thanhToanHoaDon(HoaDon hoaDon) {
        if(hoaDon == null ) {
            return false;
        }
        hoaDon.setTrangThaiThanhToan(HoaDonStatus.DA_THANH_TOAN);
        hoaDon.setTrangThai(HoaDonStatus.HOAN_TAT);
        hoaDon.setNgayDatHang(new Date());
         hoaDonRepository.save(hoaDon);
         return true;
    }

    @Override
    public List<HoaDon> findAllHoaDonCho() {
        return hoaDonRepository.findByTrangThai(HoaDonStatus.HOA_DON_CHO);
    }

    @Override
    public HoaDon findHoaDonById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    @Override
    public List<HoaDon> getAllHoaDon(String trangThai) {
        if (trangThai == null || trangThai.isEmpty()) {
            return hoaDonRepository.findAllHoaDonsOrderByNgayDatHang();  // Lấy tất cả hóa đơn nếu không có trạng thái
        } else {
            // Chuyển đổi trangThai từ String thành enum
            try {
                HoaDonStatus status = HoaDonStatus.fromValue(Integer.parseInt(trangThai));
                return hoaDonRepository.findByTrangThaiDonHang(status);  // Lọc theo trạng thái
            } catch (IllegalArgumentException e) {
                // Trường hợp trạng thái không hợp lệ, trả về tất cả
                return hoaDonRepository.findAllHoaDonsOrderByNgayDatHang();
            }
        }
    }

    @Override
    public List<HoaDon> timKiemHoaDon(Integer id, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        // Đảm bảo giá trị mặc định cho ngày bắt đầu và ngày kết thúc
        if (ngayBatDau == null) {
            ngayBatDau = LocalDateTime.of(2000, 1, 1, 0, 0);
        }
        if (ngayKetThuc == null) {
            ngayKetThuc = LocalDateTime.now();
        }
        return hoaDonRepository.findByFilters(id, ngayBatDau, ngayKetThuc);
    }

    @Override
    public Integer getCountByTrangThai(HoaDonStatus hoaDonStatus) {
        return hoaDonRepository.countHoaDonByTrangThai(hoaDonStatus);
    }


    @Override
    public Page<HoaDon> getPaginationHoaDon(Integer pageNo, HoaDonStatus trangThai , Integer idKhachHang) {
        Pageable pageable = PageRequest.of(pageNo , 3);
        return hoaDonRepository.getPaginationTrangThai(pageable , trangThai , idKhachHang);
    }
// Sử dụng cho thống kê
    @Override
    public Page<HoaDon> findTop5() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<HoaDon> hoaDons = hoaDonRepository.findTop5(pageable);
        return hoaDons ;
    }
// Sử dụng cho thống kê

    @Override
    public List<String> doanhThuTungThang(Integer year) {
        List<String> doanhThuTungThang = hoaDonRepository.thongKeTungThang(year);
        return doanhThuTungThang;
    }

    @Override
    public Double doanhThuHomNay() {
        Double doanhThDouble= hoaDonRepository.getDoanhThuHomNay(HoaDonStatus.DA_THANH_TOAN);
        return doanhThDouble;
    }

    @Override
    public Integer soSanPhamBanTrongThang() {
        Integer doanhThDouble= hoaDonRepository.getSanPhamBanTrongThang(HoaDonStatus.DA_THANH_TOAN);
        return doanhThDouble;
    }

    @Override
    public Double doanhThuTrongNam() {
        Double doanhThDouble= hoaDonRepository.getDoanhThuTrongNam(HoaDonStatus.DA_THANH_TOAN);
        return doanhThDouble;
    }

    @Override
    public List<Integer> trangThaiDonHang() {
        List<Integer> listTrangThai = hoaDonRepository.thongKeTrangThaiDonHang();
        return listTrangThai;
    }

    @Override
    public boolean changeStatus(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        if (hoaDon != null) {
            switch (hoaDon.getTrangThai()) {
                case CHO_XAC_NHAN:
                    hoaDon.setTrangThai(HoaDonStatus.DANG_XU_LY);
                    break;
                case DANG_XU_LY:
                    hoaDon.setTrangThai(HoaDonStatus.DANG_GIAO_HANG);
                    break;
                case DANG_GIAO_HANG:
                    hoaDon.setTrangThai(HoaDonStatus.HOAN_TAT);
                    break;
                case HOAN_TAT:
                    // Trạng thái đã hoàn tất, không cần thay đổi.
                    return false;
                default:
                    // Trường hợp trạng thái không hợp lệ
                    return false;
            }
            hoaDonRepository.save(hoaDon); // Lưu trạng thái mới vào cơ sở dữ liệu
            return true; // Trả về true khi trạng thái được thay đổi thành công
        }
        return false; // Trả về false nếu hóa đơn không tồn tại
    }

    @Override
    public List<Integer> getNamCoTrongHoaDon() {
        List<Integer> integers = hoaDonRepository.getNamDonHang();
        return integers;
    }


}
