package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.DiaChiRepository;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DiaChiServiceImpl implements DiaChiService {
    @Autowired
    DiaChiRepository diaChiRepository;

    @Autowired
    KhachHangRepository khachHangRepository; // Thêm repository để lấy khách hàng


    private static final int FAKE_KHACH_HANG_ID = 1;

    @Override
    public Page<DiaChi> getAllActiveDiaChi(Pageable pageable) {
        return diaChiRepository.findAllByTrangThai(Status.ACTIVE, pageable);
    }

    @Override
    public List<DiaChi> getAllActiveDiaChiByUser(KhachHang khachHang) {
        if(khachHang != null) {
            return diaChiRepository.findAllByTrangThaiAndKhachHang(Status.ACTIVE, khachHang);
        }

        return null;
    }

    @Override
    public DiaChi getDiaChiById(Integer id) {
        return diaChiRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy địa chỉ"));
    }

    @Override
    public DiaChi addDiaChi(DiaChi diaChi) {
        // Fake dữ liệu id_khach_hang cho đến khi có chức năng đăng nhập
        KhachHang fakeKhachHang = new KhachHang();
        fakeKhachHang.setId(FAKE_KHACH_HANG_ID); // Sử dụng hằng số

        diaChi.setKhachHang(fakeKhachHang);
        diaChi.setTrangThai(Status.ACTIVE);

        // Kiểm tra hợp lệ nếu cần
        validateDiaChi(diaChi);

        return diaChiRepository.save(diaChi);
    }

    @Override
    public DiaChi updateDiaChi(DiaChi diaChi, Integer idDiaChi) {
        DiaChi diaChi1 = getDiaChiById(idDiaChi);
        diaChi1.setSdt(diaChi.getSdt());
        diaChi1.setDiaChi(diaChi.getDiaChi());
        diaChi1.setTrangThai(Status.ACTIVE);
        diaChi1.setNgaySua(new Date());
        return diaChiRepository.save(diaChi1);
    }

//    @Override
//    public DiaChi updateDiaChi(DiaChi diaChi) {
//        DiaChi existingDiaChi = getDiaChiById(diaChi.getId());
//        if (existingDiaChi != null) {
//            existingDiaChi.setSdt(diaChi.getSdt());
//            existingDiaChi.setDiaChi(diaChi.getDiaChi());
//
//            // Kiểm tra hợp lệ nếu cần
//            validateDiaChi(existingDiaChi);
//
//            return diaChiRepository.save(existingDiaChi);
//        }
//        return null; // Trả về null nếu không tìm thấy địa chỉ
//    }

    @Override
    public void deleteDiaChi(Integer id) {
        DiaChi diaChi = getDiaChiById(id);
        diaChi.setTrangThai(Status.INACTIVE);
        diaChiRepository.save(diaChi);
    }

    // Phương thức kiểm tra hợp lệ
    private void validateDiaChi(DiaChi diaChi) {
        // Thêm logic kiểm tra hợp lệ nếu cần thiết
    }
}
