package com.example.kristp.service.impl;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Override
    public Boolean taoHoaDon() {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNhanVien(nhanVienRepository.findById(1).orElse(null));
        hoaDon.setTrangThai(HoaDonStatus.HOA_DON_CHO);
        if(hoaDonRepository.countByTrangThai(HoaDonStatus.HOA_DON_CHO) < 5){
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
        hoaDon.setTrangThai(HoaDonStatus.DA_THANH_TOAN);
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
    public Map<HoaDonStatus, Long> getCountByTrangThai() {
        List<Object[]> results = hoaDonRepository.countByTrangThaiGrouped();

        // Chuyển kết quả thành một Map<HoaDonStatus, Long>
        Map<HoaDonStatus, Long> countByTrangThai = new HashMap<>();
        for (Object[] result : results) {
            HoaDonStatus trangThai = (HoaDonStatus) result[0];
            Long count = (Long) result[1];
            countByTrangThai.put(trangThai, count);
        }
        return countByTrangThai;
    }


}
