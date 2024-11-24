package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.entity.dto.LocationRequest;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.DiaChiRepository;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.DiaChiService;
import com.example.kristp.utils.Authen;
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



    @Override
    public Page<DiaChi> getAllActiveDiaChi(Pageable pageable) {
        return diaChiRepository.findByTrangThaiAndKhachHang(Status.ACTIVE, Authen.khachHang,pageable);
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

        diaChi.setKhachHang(Authen.khachHang);
        diaChi.setTrangThai(Status.ACTIVE);

        // Kiểm tra hợp lệ nếu cần
        validateDiaChi(diaChi);

        return diaChiRepository.save(diaChi);
    }

    @Override
    public boolean updateDiaChi(DiaChi diaChi, Integer idDiaChi) {
        if (diaChi == null ||
                diaChi.getMaTinhThanh() == null || diaChi.getTenTinhThanh() == null ||
                diaChi.getMaQuanHuyen() == null || diaChi.getTenQuanHuyen() == null ||
                diaChi.getMaPhuongXa() == null || diaChi.getTenPhuongXa() == null) {
            // Nếu thiếu thông tin, trả về false
            return false;
        }

        // Kiểm tra nếu đã tồn tại số điện thoại trong trạng thái ACTIVE
        if(diaChiRepository.existsBySdtAndTrangThai(diaChi.getSdt(), Status.ACTIVE, idDiaChi)) {
            return false;
        }

        // Lấy đối tượng DiaChi theo ID
        DiaChi diaChi1 = getDiaChiById(idDiaChi);

        if (diaChi1 == null) {
            // Nếu không tìm thấy đối tượng, trả về false
            return false;
        }

        // Cập nhật các giá trị cho DiaChi
        diaChi1.setSdt(diaChi.getSdt());
        diaChi1.setDiaChi(diaChi.getDiaChi());
        diaChi1.setTenKhachHang(diaChi.getTenKhachHang()); // Cập nhật tên khách hàng
        diaChi1.setMaTinhThanh(diaChi.getMaTinhThanh()); // Cập nhật mã tỉnh/thành phố
        diaChi1.setTenTinhThanh(diaChi.getTenTinhThanh()); // Cập nhật tên tỉnh/thành phố
        diaChi1.setMaQuanHuyen(diaChi.getMaQuanHuyen()); // Cập nhật mã quận/huyện
        diaChi1.setTenQuanHuyen(diaChi.getTenQuanHuyen()); // Cập nhật tên quận/huyện
        diaChi1.setMaPhuongXa(diaChi.getMaPhuongXa()); // Cập nhật mã phường/xã
        diaChi1.setTenPhuongXa(diaChi.getTenPhuongXa()); // Cập nhật tên phường/xã
        diaChi1.setTrangThai(Status.ACTIVE); // Đặt trạng thái là ACTIVE
        diaChi1.setNgaySua(new Date()); // Cập nhật thời gian sửa

        // Lưu lại đối tượng đã cập nhật
        diaChiRepository.save(diaChi1);

        return true;
    }



    @Override
    public void deleteDiaChi(Integer id) {
        DiaChi diaChi = getDiaChiById(id);
        diaChi.setTrangThai(Status.INACTIVE);
        diaChiRepository.save(diaChi);
    }

    @Override
    public boolean saveDiaChi(DiaChi request) {
        if (request == null ||
                request.getMaTinhThanh() == null || request.getTenTinhThanh() == null ||
                request.getMaQuanHuyen() == null || request.getTenQuanHuyen() == null ||
                request.getMaPhuongXa() == null || request.getTenPhuongXa() == null) {
            // Nếu thiếu thông tin, trả về false
            return false;
        }

        if(diaChiRepository.existsBySdtAndTrangThai(request.getSdt(), Status.ACTIVE, 0)) {
            return false;
        }
        request.setKhachHang(Authen.khachHang);
        request.setTrangThai(Status.ACTIVE);
        diaChiRepository.save(request);
        return true; // Lưu thành

    }

    // Phương thức kiểm tra hợp lệ
    private void validateDiaChi(DiaChi diaChi) {
        // Thêm logic kiểm tra hợp lệ nếu cần thiết
    }
}
