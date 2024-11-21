package com.example.kristp.service.impl;

import com.example.kristp.entity.NhanVien;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    private static final int FAKE_Tai_khoan_ID = 1;

    @Override
    public ArrayList<NhanVien> getAllNhanVien() {
        return (ArrayList<NhanVien>) nhanVienRepository.findAll();
    }

    @Override
    public ArrayList<NhanVien> getAllNhanVienHD() {
        return (ArrayList<NhanVien>) nhanVienRepository.findAllNhanVien();
    }

    @Override
    public NhanVien getNhanVienById(int id) {
        return nhanVienRepository.findById(id).get();
    }

    @Override
    public NhanVien addNhanVien(NhanVien nhanVien) {
        if (timTheoMaNhanVien(nhanVien.getMaNhanVien()) != null){
            return null;
        }
        TaiKhoan fakeTaiKhoan = new TaiKhoan();
        fakeTaiKhoan.setId(FAKE_Tai_khoan_ID);
        nhanVien.setTaiKhoan(fakeTaiKhoan);
        nhanVien.setTrangThai(Status.ACTIVE);
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien updateNhanVien(NhanVien nhanVien, Integer idNhanVien) {
        NhanVien timma = timTheoMaNhanVien(nhanVien.getMaNhanVien());
        if (timma != null && !timma.getId().equals(idNhanVien)){
            return null;
        }
        NhanVien nhanVien1 = getNhanVienById(idNhanVien);
        nhanVien1.setMaNhanVien(nhanVien.getMaNhanVien());
        nhanVien1.setTenNhanVien(nhanVien.getTenNhanVien());
        nhanVien1.setSoDienThoai(nhanVien.getSoDienThoai());
        nhanVien1.setNgaySinh(nhanVien.getNgaySinh());
        nhanVien1.setDiaChi(nhanVien.getDiaChi());
//        nhanVien1.setTrangThai(nhanVien.getTrangThai());
        return nhanVienRepository.save(nhanVien1);
    }

    @Override
    public void deleteNhanVien(Integer idNhanVien) {
        NhanVien nhanVien = getNhanVienById(idNhanVien);
        if (nhanVien != null) {
            // Kiểm tra trạng thái hiện tại và thay đổi theo yêu cầu
            if (Status.ACTIVE.equals(nhanVien.getTrangThai())) {
                nhanVien.setTrangThai(Status.INACTIVE);  // Nếu đang hoạt động, chuyển thành không hoạt động
            } else if (Status.INACTIVE.equals(nhanVien.getTrangThai())) {
                nhanVien.setTrangThai(Status.ACTIVE);  // Nếu không hoạt động, chuyển lại thành đang hoạt động
            }

            // Lưu lại trạng thái mới vào cơ sở dữ liệu
            nhanVienRepository.save(nhanVien);
        }
    }

    @Override
    public NhanVien timTheoMaNhanVien(String maNhanVien) {
        Optional<NhanVien> nhanVien = nhanVienRepository.timKiemMaNhanVien(maNhanVien);
        if (nhanVien.isPresent()){
            return nhanVien.get();
        }
        return null;
    }

    @Override
    public Page<NhanVien> getPaginationNhanVien(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public Page<NhanVien> timTatCaTheoMa(Integer pageNo, String ma) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return nhanVienRepository.findAllByMaLike(pageable, ma);
    }
}
