package com.example.kristp.service.impl;

import com.example.kristp.entity.ChiTietSanPham;
import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.HoaDonChiTiet;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.repository.HoaDonChiTietRepo;
import com.example.kristp.repository.HoaDonRepository;
import com.example.kristp.service.BanHangService;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class BanHangServiceImpl implements BanHangService {

    @Autowired
    HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    KhuyenMaiService khuyenMaiService;
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
        if (khuyenMai == null) {
            return false;
        } else {
            hoaDon.setKhuyenMai(khuyenMai);
            hoaDonRepository.save(hoaDon);
        }
        return true;
    }

    @Override
    public Float findTongTienKhuyenMai(HoaDon hoaDon) {
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
