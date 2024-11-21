package com.example.kristp.service.impl;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.KhuyenMai;
import com.example.kristp.entity.MauSac;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.KhuyenMaiRepository;
import com.example.kristp.service.KhuyenMaiService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Override
    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        return (ArrayList<KhuyenMai>) khuyenMaiRepository.findAll();
    }

    @Override
    public KhuyenMai getKhuyenMaiById(int id) {
        return khuyenMaiRepository.findById(id).get();
    }

    @Override
    public KhuyenMai addKhuyenMai(KhuyenMai khuyenMai) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        // Kiểm tra nếu trạng thái là "Chưa kích hoạt" và ngày bắt đầu là ngày mai
        if ("Chưa kích hoạt".equals(khuyenMai.getTrangThai()) && khuyenMai.getNgayBatDau().after(today)) {
            khuyenMai.setTrangThai("Chưa kích hoạt");
        } else if ("Chưa kích hoạt".equals(khuyenMai.getTrangThai()) && today.equals(khuyenMai.getNgayBatDau())) {
            // Nếu ngày bắt đầu là hôm nay và trạng thái là "Chưa kích hoạt", tự động chuyển trạng thái thành "Đang hoạt động"
            khuyenMai.setTrangThai("Đang hoạt động");
        } else if (today.after(khuyenMai.getNgayBatDau())) {
            // Nếu ngày bắt đầu là hôm nay, set trạng thái là "Đang hoạt động"
            khuyenMai.setTrangThai("Đang hoạt động");
        }



        // Lưu khuyến mại vào cơ sở dữ liệu
        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateKhuyenMaiStatus() {
        // Lấy tất cả khuyến mại
        var khuyenMais = khuyenMaiRepository.findAll();

        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        for (KhuyenMai km : khuyenMais) {
            if (km.getNgayKetThuc().before(new Date())) {
                // Nếu ngày kết thúc đã qua, chuyển trạng thái sang "Hết hạn"
                km.setTrangThai("Hết hạn");
                khuyenMaiRepository.save(km);
            }
            else if ("Chưa kích hoạt".equals(km.getTrangThai()) && km.getNgayBatDau().before(today)) {
                km.setTrangThai("Đang hoạt động");
                khuyenMaiRepository.save(km);
            }
        }
    }

    @Override
    public KhuyenMai updateKhuyenMai(KhuyenMai khuyenMai, Integer idKhuyenMai) {
        KhuyenMai khuyenmaitheoten = timTheoTenKhuyenMai(khuyenMai.getTenKhuyenMai());
        if (khuyenmaitheoten != null && !khuyenmaitheoten.getId().equals(idKhuyenMai)) {
            return null;
        }

        // Lấy ngày hiện tại và đặt thời gian về 00:00:00
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        // Kiểm tra trạng thái và ngày bắt đầu
        if ("Chưa kích hoạt".equals(khuyenMai.getTrangThai()) && khuyenMai.getNgayBatDau().after(today)) {
            khuyenMai.setTrangThai("Chưa kích hoạt");
        } else if ("Chưa kích hoạt".equals(khuyenMai.getTrangThai()) && today.equals(khuyenMai.getNgayBatDau())) {
            khuyenMai.setTrangThai("Đang hoạt động");
        } else if (today.after(khuyenMai.getNgayBatDau())) {
            khuyenMai.setTrangThai("Đang hoạt động");
        }

        // Kiểm tra trạng thái và ngày kết thúc
        if (khuyenMai.getNgayKetThuc() != null) {
            if (khuyenMai.getNgayKetThuc().before(today) || khuyenMai.getNgayKetThuc().equals(today)) {
                khuyenMai.setTrangThai("Hết hạn");
            } else if (khuyenMai.getNgayKetThuc().after(today)) {
                khuyenMai.setTrangThai("Đang hoạt động");
            }
        }

        // Cập nhật thông tin khuyến mãi
        KhuyenMai khuyenMai1 = getKhuyenMaiById(idKhuyenMai);
        khuyenMai1.setMaKhuyenMai(khuyenMai.getMaKhuyenMai());
        khuyenMai1.setTenKhuyenMai(khuyenMai.getTenKhuyenMai());
        khuyenMai1.setKieuKhuyenMai(khuyenMai.getKieuKhuyenMai());
        khuyenMai1.setGiaTri(khuyenMai.getGiaTri());
        khuyenMai1.setMucGiamToiDa(khuyenMai.getMucGiamToiDa());
        khuyenMai1.setNgayBatDau(khuyenMai.getNgayBatDau());
        khuyenMai1.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        khuyenMai1.setTrangThai(khuyenMai.getTrangThai());

        return khuyenMaiRepository.save(khuyenMai1);
    }


    @Override
    public void deleteKhuyenMai(Integer idKhuyenMai) {
        KhuyenMai khuyenMai = getKhuyenMaiById(idKhuyenMai);
        // Kiểm tra trạng thái hiện tại và thay đổi theo yêu cầu
        if ("Đang hoạt động".equals(khuyenMai.getTrangThai())) {
            khuyenMai.setTrangThai("Hết hạn");
        } else if ("Hết hạn".equals(khuyenMai.getTrangThai())) {
            khuyenMai.setTrangThai("Đang hoạt động");
        }
        khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public KhuyenMai timTheoTenKhuyenMai(String tenKhuyenMai) {
        Optional<KhuyenMai> khuyenMai = khuyenMaiRepository.timKiemTenKhuyenMai(tenKhuyenMai);
        if (khuyenMai.isPresent()) {
            return khuyenMai.get();
        } else {
            return null;
        }
    }

    @Override
    public KhuyenMai timTheoMaKhuyenMai(String maKhuyenMai) {
        Optional<KhuyenMai> khuyenMai1 = khuyenMaiRepository.timKiemMaKhuyenMai(maKhuyenMai);
        if (khuyenMai1.isPresent()) {
            return khuyenMai1.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<KhuyenMai> getPaginationKhuyenMai(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return khuyenMaiRepository.findAll(pageable);
    }

    @Override
    public Page<KhuyenMai> timKiemKhuyenMai(String maKhuyenMai, String tenKhuyenMai, Boolean kieuKhuyenMai, Float mucGiamToiDa, String trangThai, Date ngayBatDau, Date ngayKetThuc, Pageable pageable) {
        return khuyenMaiRepository.findKhuyenMaiByCriteria(maKhuyenMai, tenKhuyenMai, kieuKhuyenMai, mucGiamToiDa, trangThai, ngayBatDau, ngayKetThuc, pageable);

    }
    public String formatCurrency(Float value) {
        if (value != null) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(value);
        }
        return "0";
    }

}
