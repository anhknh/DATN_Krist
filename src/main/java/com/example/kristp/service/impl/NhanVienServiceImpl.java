package com.example.kristp.service.impl;

import com.example.kristp.entity.NhanVien;
import com.example.kristp.entity.TaiKhoan;
import com.example.kristp.entity.dto.NhanVienDto;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.NhanVienRepository;
import com.example.kristp.repository.TaiKhoanRepository;
import com.example.kristp.service.NhanVienService;
import com.example.kristp.service.TaiKhoanService;
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
    @Autowired
    TaiKhoanService taiKhoanService;


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
    public NhanVien addNhanVien(NhanVienDto nhanVienDto) {
        if (timTheoMaNhanVien(nhanVienDto.getMaNhanVien()) != null){
            return null;
        }
        TaiKhoan fakeTaiKhoan = new TaiKhoan();
        fakeTaiKhoan.setTenDangNhap(nhanVienDto.getTenDangNhap());
        fakeTaiKhoan.setMatKhau(nhanVienDto.getMatKhau());
        fakeTaiKhoan.setEmail(nhanVienDto.getEmail());
        TaiKhoan newTaiKhoan = taiKhoanService.taoTaiKhoanAdmin(fakeTaiKhoan);
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNhanVien(nhanVienDto.getMaNhanVien());
        nhanVien.setTenNhanVien(nhanVienDto.getTenNhanVien());
        nhanVien.setSoDienThoai(nhanVienDto.getSoDienThoai());
        nhanVien.setNgaySinh(nhanVienDto.getNgaySinh());
        nhanVien.setDiaChi(nhanVienDto.getDiaChi());
        nhanVien.setTaiKhoan(newTaiKhoan);
        nhanVien.setTrangThai(Status.ACTIVE);
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien updateNhanVien(NhanVienDto nhanVien, Integer idNhanVien) {
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
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        if (nhanVien != null) {
            // Kiểm tra trạng thái hiện tại và thay đổi theo yêu cầu
            if (Status.ACTIVE.equals(nhanVien.getTrangThai())) {
                nhanVien.setTrangThai(Status.INACTIVE);  // Nếu đang hoạt động, chuyển thành không hoạt động
                taiKhoan.setTrangThai(Status.INACTIVE);
            } else if (Status.INACTIVE.equals(nhanVien.getTrangThai())) {
                nhanVien.setTrangThai(Status.ACTIVE);  // Nếu không hoạt động, chuyển lại thành đang hoạt động
                taiKhoan.setTrangThai(Status.ACTIVE);
            }

            // Lưu lại trạng thái mới vào cơ sở dữ liệu
            nhanVienRepository.save(nhanVien);
        }
    }

    @Override
    public NhanVien timTheoMaNhanVien(String maNhanVien) {
        Optional<NhanVien> nhanVien = nhanVienRepository.timKiemMaNhanVien(maNhanVien);
        return nhanVien.orElse(null);
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

    @Override
    public NhanVienDto fetchNhanVien(String maNhanVien) {
        NhanVien  nhanVien = nhanVienRepository.timKiemMaNhanVien(maNhanVien).orElse(null);
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        NhanVienDto nhanVienDto = new NhanVienDto();
        nhanVienDto.setId(nhanVien.getId());
        nhanVienDto.setEmail(taiKhoan.getEmail());
        nhanVienDto.setMaNhanVien(maNhanVien);
        nhanVienDto.setMatKhau(taiKhoan.getMatKhau());
        nhanVienDto.setEmail(taiKhoan.getEmail());
        nhanVienDto.setTenDangNhap(taiKhoan.getTenDangNhap());
        nhanVienDto.setDiaChi(nhanVien.getDiaChi());
        nhanVienDto.setNgaySinh(nhanVien.getNgaySinh());
        nhanVienDto.setSoDienThoai(nhanVien.getSoDienThoai());
        nhanVienDto.setTenNhanVien(nhanVien.getTenNhanVien());
        return nhanVienDto;
    }
}
