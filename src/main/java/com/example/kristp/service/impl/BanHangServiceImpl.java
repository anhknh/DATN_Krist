package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.enums.HoaDonStatus;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.KhachHangService;
import com.example.kristp.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BanHangServiceImpl implements BanHangService {

    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    KhuyenMaiService khuyenMaiService;
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    private HoaDonRepository hoaDonRepository;


    @Override
    public HoaDonChiTiet addGioHang(HoaDon hoaDon, Integer idChiTietSanPham,String qrCode , Integer soLuong) {
        ChiTietSanPham chiTietSanPham = null;
        System.out.println(qrCode);
        if(qrCode != null) {
            chiTietSanPham = chiTietSanPhamRepository.findByQrCode(qrCode);
        } else {
            chiTietSanPham = chiTietSanPhamService.getCTSPById(idChiTietSanPham);
        }

        if(chiTietSanPham.getSoLuong() < soLuong) {
            return null;
        }

        HoaDonChiTiet chiTiet  = hoaDonChiTietRepo.findByHoaDonAndChiTietSanPham(hoaDon, chiTietSanPham);
        if (chiTiet == null) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPham);
            hoaDonChiTiet.setGiaTien(chiTietSanPham.getDonGia());
            hoaDonChiTiet.setSoLuong(soLuong);
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - soLuong);
            chiTietSanPhamRepository.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet);
        } else {
            chiTiet.setSoLuong(chiTiet.getSoLuong() + soLuong);
            chiTiet.setGiaTien(chiTietSanPham.getDonGia());
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - soLuong);
            chiTietSanPhamRepository.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(chiTiet);
        }

    }

    @Override
    public Float getTongTien(HoaDon hoaDon) {
        if (hoaDon == null) {
            return 0f;
        }
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepo.getHoaDonChiTietByHoaDon(hoaDon, null);
        float tongTien = 0f;
        for (HoaDonChiTiet chiTiet : hoaDonChiTietPage.getContent()) {
            tongTien = tongTien + (chiTiet.getGiaTien() * chiTiet.getSoLuong());
        }
        return tongTien;
    }

    @Override
    public boolean addKhuyenMai(Integer idKhuyenMai, HoaDon hoaDon) {
        KhuyenMai khuyenMai = khuyenMaiService.getKhuyenMaiById(idKhuyenMai);
        if (khuyenMai == null || !khuyenMai.getTrangThai().equals("Đang hoạt động")) {
            return false;
        } else {
            hoaDon.setKhuyenMai(khuyenMai);
            hoaDonRepository.save(hoaDon);
        }
        return true;
    }

    @Override
    public boolean huyKhuyenMai(Integer idKhuyenMai, HoaDon hoaDon) {
        KhuyenMai khuyenMai = khuyenMaiService.getKhuyenMaiById(idKhuyenMai);
        if (khuyenMai == null) {
            return false;
        } else {
            hoaDon.setKhuyenMai(null);
            hoaDonRepository.save(hoaDon);
        }
        return true;
    }

    @Override
    public boolean addKhachHang(Integer idKhachHang, String soDienThoai, String tenKhachHang, HoaDon hoaDon) {
        KhachHang khachHang;

        // Kiểm tra khách hàng đã tồn tại chưa
        if (idKhachHang != null) {
            khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
            if (khachHang == null) {
                // Trường hợp ID khách hàng không tồn tại
                return false;
            }
        } else {
            // Tìm khách hàng theo số điện thoại
            khachHang = khachHangRepository.findBySoDienThoai(soDienThoai).orElse(null);

            if (khachHang == null) {
                // Tạo mới khách hàng nếu chưa tồn tại
                khachHang = new KhachHang();
//                if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
//                    khachHang.setTenKhachHang("Khách vãng lai");
//                }
//
//                // Nếu số điện thoại không có, gán mặc định
//                if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
//                    khachHang.setSoDienThoai("Trống");
//                }
                khachHang.setTenKhachHang(tenKhachHang);
                khachHang.setSoDienThoai(soDienThoai);
                khachHangRepository.save(khachHang);
            }
        }

        // Gắn khách hàng vào hóa đơn
        hoaDon.setKhachHang(khachHang);

        // Lưu hóa đơn
        hoaDonRepository.save(hoaDon);

        return true;
    }

    @Override
    public boolean huyKhachHang(HoaDon hoaDon) {
        hoaDon.setKhachHang(null);
        hoaDonRepository.save(hoaDon);
        return true;
    }

    @Override
    public boolean huyDonHang(Integer idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElse(null);
        if (hoaDon != null) {
            if(hoaDon.getTrangThai() == HoaDonStatus.CHO_XAC_NHAN || hoaDon.getTrangThai() == HoaDonStatus.HOA_DON_CHO) {
                hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepo.getHoaDonChiTietByHoaDonList(hoaDon);
                for (HoaDonChiTiet chiTiet : hoaDonChiTietList) {
                    ChiTietSanPham sanPham = chiTiet.getChiTietSanPham();
                    sanPham.setSoLuong(sanPham.getSoLuong() + chiTiet.getSoLuong());
                    chiTietSanPhamRepository.save(sanPham);
                }
                hoaDonRepository.save(hoaDon);
                return true;
            }
            return false;
        }
        return false;
    }


    @Override
    public Float findTongTienKhuyenMai(HoaDon hoaDon) {
        if(hoaDon == null) {
            return 0f;
        }
        Float tongTien = getTongTien(hoaDon);
        if(hoaDon.getKhuyenMai() == null) {
            return tongTien;
        }
        if(Objects.equals(hoaDon.getKhuyenMai().getKieuKhuyenMai(), "VND")) {
            tongTien = tongTien - hoaDon.getKhuyenMai().getGiaTri();
        }
        return tongTien;
    }

    @Override
    public boolean xoaSanPhamGioHang(Integer inHoaDonChiTiet) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(inHoaDonChiTiet).orElse(null);
        if(hoaDonChiTiet != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(hoaDonChiTiet.getChiTietSanPham().getId());
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + hoaDonChiTiet.getSoLuong());
            chiTietSanPhamRepository.save(chiTietSanPham);
            hoaDonChiTietRepo.delete(hoaDonChiTiet);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSoLuongGioHang(Integer inHoaDonChiTiet, Integer idSoLuong) {
        // Tìm bản ghi hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(inHoaDonChiTiet).orElse(null);

        if (hoaDonChiTiet != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(hoaDonChiTiet.getChiTietSanPham().getId());

            if (chiTietSanPham != null) {
                // Lấy số lượng hiện tại trong hóa đơn chi tiết để tính chênh lệch
                int soLuongCu = hoaDonChiTiet.getSoLuong();
                int chenhLechSoLuong = idSoLuong - soLuongCu;

                // Kiểm tra nếu cập nhật làm giảm số lượng sản phẩm trong kho quá giới hạn
                if (chiTietSanPham.getSoLuong() - chenhLechSoLuong < 0) {
                    return false;
                }

                hoaDonChiTiet.setSoLuong(idSoLuong);
                hoaDonChiTietRepo.save(hoaDonChiTiet);

                chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - chenhLechSoLuong);
                chiTietSanPhamRepository.save(chiTietSanPham);

                return true;
            }
        }

        return false;
    }


}
