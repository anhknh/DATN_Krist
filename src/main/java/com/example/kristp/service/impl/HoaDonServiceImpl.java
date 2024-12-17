package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.HoaDonChiTietService;
import com.example.kristp.service.HoaDonService;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    BanHangService banHangService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DataUtils dataUtils;

    @Override
    public Boolean taoHoaDon() {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNhanVien(nhanVienRepository.findById(Authen.nhanVien.getId()).orElse(null));
        hoaDon.setTrangThai(HoaDonStatus.HOA_DON_CHO);
        hoaDon.setTrangThaiThanhToan(HoaDonStatus.CHUA_THANH_TOAN);
        hoaDon.setTongTien(BigDecimal.valueOf(0));
        hoaDon.setNgayDatHang(new Date());
        hoaDon.setHinhThucThanhToan("offline");
        hoaDon.setPhiVanChuyen(0f);
        hoaDon.setMaHoaDon(dataUtils.generateBarcode(8));
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
        hoaDon.setTongTien(BigDecimal.valueOf(banHangService.getTongTien(hoaDon)));
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
    public Page<HoaDon> getAllHoaDon(String trangThai, Pageable pageable) {
        if (trangThai == null || trangThai.isEmpty()) {
            return hoaDonRepository.findAllHoaDonsOrderByNgayDatHang(pageable);  // Lấy tất cả hóa đơn nếu không có trạng thái
        } else {
            // Chuyển đổi trangThai từ String thành enum
            try {
                HoaDonStatus status = HoaDonStatus.fromValue(Integer.parseInt(trangThai));
                return hoaDonRepository.findByTrangThaiDonHang(status, pageable);  // Lọc theo trạng thái
            } catch (IllegalArgumentException e) {
                // Trường hợp trạng thái không hợp lệ, trả về tất cả
                return hoaDonRepository.findAllHoaDonsOrderByNgayDatHang(pageable);
            }
        }
    }

    @Override
    public Page<HoaDon> timKiemHoaDon(String id, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, Pageable pageable) {
        // Đảm bảo giá trị mặc định cho ngày bắt đầu và ngày kết thúc
        if (ngayBatDau == null) {
            ngayBatDau = LocalDateTime.of(2000, 1, 1, 0, 0);
        }
        if (ngayKetThuc == null) {
            ngayKetThuc = LocalDateTime.now();
        }
        return hoaDonRepository.findByFilters(id, ngayBatDau, ngayKetThuc, pageable);
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
        Double doanhThDouble= hoaDonRepository.getDoanhThuHomNay(HoaDonStatus.DA_THANH_TOAN , HoaDonStatus.HOAN_TAT);
        return doanhThDouble;
    }

    @Override
    public Integer soSanPhamBanTrongThang() {
        Integer doanhThDouble= hoaDonRepository.getSanPhamBanTrongThang(HoaDonStatus.DA_THANH_TOAN, HoaDonStatus.HOAN_TAT);
        return doanhThDouble;
    }

    @Override
    public Double doanhThuTrongNam() {
        Double doanhThDouble= hoaDonRepository.getDoanhThuTrongNam(HoaDonStatus.DA_THANH_TOAN ,  HoaDonStatus.HOAN_TAT);
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
                    hoaDon.setTrangThaiThanhToan(HoaDonStatus.DA_THANH_TOAN);
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
    public boolean changePhiVanChuyen(Integer id, double phiVanChuyen) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        if(hoaDon != null) {
            if(hoaDon.getTrangThai() == HoaDonStatus.CHO_XAC_NHAN || hoaDon.getTrangThai() == HoaDonStatus.DANG_XU_LY) {
                hoaDon.setPhiVanChuyen((float) phiVanChuyen);
                hoaDonRepository.save(hoaDon);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Integer> getNamCoTrongHoaDon() {
        List<Integer> integers = hoaDonRepository.getNamDonHang();
        return integers;
    }

    @Override
    public boolean xoaSanPham(Integer idHoaDon) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(idHoaDon).orElse(null);
        if(hoaDonChiTiet != null && hoaDonChiTiet.getHoaDon().getTrangThai() == HoaDonStatus.CHO_XAC_NHAN) {

            ChiTietSanPham chiTietSanPham = hoaDonChiTiet.getChiTietSanPham();
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();
            chiTietSanPham .setSoLuong(hoaDonChiTiet.getSoLuong() + chiTietSanPham.getSoLuong());
            double tienTru = (Float.parseFloat(hoaDon.getTongTien() + "") -  (hoaDonChiTiet.getGiaTien() * hoaDonChiTiet.getSoLuong()));
            if(tienTru < 0) {
                hoaDon.setTongTien(BigDecimal.valueOf(0));
            } else {
                hoaDon.setTongTien(BigDecimal.valueOf(tienTru));
            }
            chiTietSanPhamRepository.save(chiTietSanPham);
            hoaDonRepository.save(hoaDon);
            hoaDonChiTietRepo.delete(hoaDonChiTiet);
            return true;
        }

        return false;
    }

    @Override
    public boolean capNhatSoLuong(Integer idHoaDonChiTiet, int soLuongMoi) {
        // Tìm hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(idHoaDonChiTiet).orElse(null);

        // Kiểm tra hóa đơn chi tiết và trạng thái hóa đơn
        if (hoaDonChiTiet != null && hoaDonChiTiet.getHoaDon().getTrangThai() == HoaDonStatus.CHO_XAC_NHAN) {
            ChiTietSanPham chiTietSanPham = hoaDonChiTiet.getChiTietSanPham();
            HoaDon hoaDon = hoaDonChiTiet.getHoaDon();

            int soLuongCu = hoaDonChiTiet.getSoLuong();

            // Tính số lượng chênh lệch
            int chenhLechSoLuong = soLuongMoi - soLuongCu;

            // Kiểm tra nếu số lượng mới không khả dụng trong kho
            if (chenhLechSoLuong > 0 && chiTietSanPham.getSoLuong() < chenhLechSoLuong) {
                return false; // Không đủ hàng trong kho
            }

            // Cập nhật số lượng sản phẩm trong kho
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - chenhLechSoLuong);

            // Cập nhật số lượng trong hóa đơn chi tiết
            hoaDonChiTiet.setSoLuong(soLuongMoi);

            // Cập nhật tổng tiền hóa đơn
            double tongTienCu = hoaDon.getTongTien().doubleValue();
            double tongTienMoi = tongTienCu + (hoaDonChiTiet.getGiaTien() * chenhLechSoLuong);
            hoaDon.setTongTien(BigDecimal.valueOf(Math.max(tongTienMoi, 0))); // Tổng tiền không được âm

            // Lưu cập nhật vào cơ sở dữ liệu
            chiTietSanPhamRepository.save(chiTietSanPham);
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            hoaDonRepository.save(hoaDon);

            return true; // Cập nhật thành công
        }

        return false; // Không tìm thấy hóa đơn chi tiết hoặc trạng thái không phù hợp
    }

    @Override
    public boolean themSanPhamDon(Integer idHoaDon, Integer idChiTiet) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElse(null);
        if(hoaDon != null && hoaDon.getTrangThai() == HoaDonStatus.CHO_XAC_NHAN) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idChiTiet).orElse(null);
            if(hoaDonChiTietRepo.existsByHoaDon_IdAndChiTietSanPham_Id(idHoaDon, idChiTiet)) {
                HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findByHoaDonAndChiTietSanPham(hoaDon, chiTietSanPham);
                hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + 1);
                hoaDon.setTongTien(BigDecimal.valueOf(Float.parseFloat(hoaDon.getTongTien() + "") + hoaDonChiTiet.getGiaTien()));
                if(chiTietSanPham.getSoLuong() >= 1) {
                    chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - 1);
                } else {
                    return false;
                }
                hoaDonRepository.save(hoaDon);
                chiTietSanPhamRepository.save(chiTietSanPham);
                hoaDonChiTietRepo.save(hoaDonChiTiet);
                return true;

            } else {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setChiTietSanPham(chiTietSanPham);
                hoaDonChiTiet.setGiaTien(chiTietSanPham.getDonGia());
                hoaDonChiTiet.setSoLuong(1);
                hoaDon.setTongTien(BigDecimal.valueOf(Float.parseFloat(hoaDon.getTongTien() + "") + chiTietSanPham.getDonGia()));
                if(chiTietSanPham.getSoLuong() >= 1) {
                    chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - 1);
                } else {
                    return false;
                }
                hoaDonChiTietRepo.save(hoaDonChiTiet);
                chiTietSanPhamRepository.save(chiTietSanPham);
                hoaDonRepository.save(hoaDon);
                return true;
            }
        }
        return false;
    }


}
