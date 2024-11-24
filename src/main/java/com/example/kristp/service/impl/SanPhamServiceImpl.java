package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.repository.SanPhamRepository;
import com.example.kristp.service.ChatLieuService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    DanhMucRepository danhMucRepository;
    @Autowired
    ChatLieuRepository chatLieuRepository;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;

    public ArrayList<SanPham> findAllSanPham() {
        return (ArrayList<SanPham>) sanPhamRepository.findAll();
    }

    public ArrayList<SanPham> findSanphamTop4ngaytaoDesc() {
//        return sanPhamRepository.findFirst5ByOrderByNgayTaoDesc();
        return null;
    }

    public SanPham findSanphamById(Integer id) {
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public SanPham addSanPham(SanPham sanPham) {
        if(timTheoTenSanPham(sanPham.getTenSanPham()) != null){
            return null ;
        }
        sanPham.setTrangThai(Status.ACTIVE);
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public SanPham updateSanPham(SanPham sanPham, Integer idSanPham) {
        SanPham timtheoten = timTheoTenSanPham(sanPham.getTenSanPham());
        if(timtheoten != null && !timtheoten.getId().equals(idSanPham)) {
            return null;
        }

        SanPham tayao1 = findSanphamById(idSanPham);
        tayao1.setChatLieu(sanPham.getChatLieu());
        tayao1.setDanhMuc(sanPham.getDanhMuc());
        tayao1.setTenSanPham(sanPham.getTenSanPham());
        tayao1.setMoTa(sanPham.getMoTa());
        tayao1.setTrangThai(sanPham.getTrangThai());
        return sanPhamRepository.save(tayao1);
    }

    @Override
    public void deleteSanPham(Integer idSanPham) {
        SanPham sanPham = sanPhamRepository.findById(idSanPham).orElse(null);
        if (sanPham != null) {
            // Kiểm tra trạng thái hiện tại và thay đổi theo yêu cầu
            if (Status.ACTIVE.equals(sanPham.getTrangThai())) {
                sanPham.setTrangThai(Status.INACTIVE);  // Nếu đang hoạt động, chuyển thành không hoạt động
            } else if (Status.INACTIVE.equals(sanPham.getTrangThai())) {
                sanPham.setTrangThai(Status.ACTIVE);  // Nếu không hoạt động, chuyển lại thành đang hoạt động
            }

            // Lưu lại trạng thái mới vào cơ sở dữ liệu
            sanPhamRepository.save(sanPham);
        }
    }

//    public Integer addSanpham(Integer danhMuc, Integer chatLieu, String tenSanPham, String moTa, Status trangThai) {
//        SanPham sanPham = new SanPham();
//        DanhMuc danhMuc1 = danhMucService.getDanhmucById(danhMuc);
//        sanPham.setDanhMuc(danhMuc1);
//        ChatLieu chatLieu1 = chatLieuService.getChatlieuById(chatLieu);
//        sanPham.setChatLieu(chatLieu1);
//        sanPham.setTenSanPham(tenSanPham);
//        sanPham.setMoTa(moTa);
//        sanPham.setTrangThai(trangThai);
//        SanPham saveSanPham = sanPhamRepository.save(sanPham);
//        return saveSanPham.getId(); // Trả về ID của sản phẩm vừa thêm
//    }
//
//    // Phương thức để cập nhật sản phẩm
//    public void updateSanPham(Integer id, Integer danhMuc, Integer chatLieu, String tenSanPham, String moTa, Status trangThai) {
//        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(() -> new RuntimeException("khong thay san pham"));
//        DanhMuc danhMuc1 = danhMucService.getDanhmucById(danhMuc);
//        sanPham.setDanhMuc(danhMuc1);
//        ChatLieu chatLieu1 = chatLieuService.getChatlieuById(chatLieu);
//        sanPham.setChatLieu(chatLieu1);
//        sanPham.setTenSanPham(tenSanPham);
//        sanPham.setMoTa(moTa);
//        sanPham.setTrangThai(trangThai);
//        sanPhamRepository.save(sanPham);
//    }
//    public SanPham add(SanPham sanPham) {
//        return sanPhamRepository.save(sanPham);
//    }
//
//    public SanPham update(SanPham sanPham, Integer id) {
//        sanPham.setId(id);
//        return sanPhamRepository.save(sanPham);
//    }
//    public SanPham delete(Integer id) {
//        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
//        sanPhamRepository.delete(sanPham);
//        return sanPham;
//    }

    public Page<SanPham> getAllSanphamPhantrang(Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        return sanPhamRepository.findAll(pageable);
    }

    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    public boolean isTenExists(String tenSanPham) {
        return sanPhamRepository.existsByTenSanPham(tenSanPham);
    }

    @Override
    public SanPham timTheoTenSanPham(String tenSanPham) {
        return null;
    }

    @Override
    public Page<SanPham> getPaginationSanPham(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 9);
        return sanPhamRepository.findAllSanPham(pageable);
    }

    @Override
    public Page<SanPham> timTatCaTheoTen(Integer pageNo, String ten) {
        Pageable pageable = PageRequest.of(pageNo , 6);
        return sanPhamRepository.findAllByTenLike(pageable,ten);
    }


}
