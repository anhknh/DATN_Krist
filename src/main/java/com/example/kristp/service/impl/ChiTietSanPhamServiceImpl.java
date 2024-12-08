package com.example.kristp.service.impl;

import com.example.kristp.entity.*;
import com.example.kristp.entity.dto.ChiTietSanPhamDto;
import com.example.kristp.entity.dto.ChiTietSanPhamDtoEdit;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.*;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    MauSacRepository mauSacRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    TayAoRepository tayAoRepository;
    @Autowired
    CoAoRepository coAoRepository;
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    FileUpload fileUpload;




    @Override
    public ArrayList<ChiTietSanPham> getAllCTSP() {
        return (ArrayList<ChiTietSanPham>) chiTietSanPhamRepository.findAll();
    }

    @Override
    public ChiTietSanPham getCTSPById(int id) {
        return chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy chi tiết sản phẩm với id: " + id));
    }

    @Override
    public ChiTietSanPham findFristProductDetail(SanPham sanPham) {
        return chiTietSanPhamRepository.findFirstBySanPham(sanPham);
    }

    @Override
    public List<MauSac> getColorsByProductId(Integer productId) {
        return chiTietSanPhamRepository.findDistinctColorsByProductId(productId);
    }

    @Override
    public List<Size> getSizesByProductId(Integer productId) {
        return chiTietSanPhamRepository.findDistinctSizesByProductId(productId);
    }

    @Override
    public ChiTietSanPham getProductDetailByColorAndSize(Integer productId, Integer colorId, Integer sizeId) {
        return chiTietSanPhamRepository.findProductDetailByColorAndSize(productId, colorId, sizeId);
    }

    @Override
    public List<ChiTietSanPham> getProductDetailsByProductId(Integer productId) {
        return chiTietSanPhamRepository.findAllProductDetailsByProductId(productId);
    }

    @Override
    public List<ChiTietSanPham> getAllChiTietSanPhamSPHD() {
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamRepository.findAllProductDetailsSPHD();
        return chiTietSanPhamList;
    }

    @Override
    public List<Integer> getSizeIdByProductIdAndColorId(Integer productId, Integer colorId) {
        return chiTietSanPhamRepository.getSizeByIdProductAndIdColor(productId, colorId);
    }

    @Override
    public ChiTietSanPham getByColorAndSize(Integer colorId, Integer sizeId, Integer productId) {
        return chiTietSanPhamRepository.findByMau_IdAndSize_IdAndSanPham_Id(colorId, sizeId, productId);
    }

    @Override
    public boolean addChioTietSanPham(List<ChiTietSanPhamDto> chiTietSanPham, Integer idSanPham) throws IOException {

        SanPham sanPham = sanPhamRepository.findById(idSanPham).orElse(null);
        if(sanPham == null || sanPham.getTrangThai() == Status.INACTIVE) {
            return false;
        }
        for (ChiTietSanPhamDto chiTietSanPhamDto : chiTietSanPham) {
            if(chiTietSanPhamRepository.existsBySanPhamAndMauAndSizeAndQrCodeExcludeItself(idSanPham, chiTietSanPhamDto.getIdMauSac(),
                    chiTietSanPhamDto.getIdSize(), chiTietSanPhamDto.getQrCode(), null)) {
                return false;
            }
            String contentType = chiTietSanPhamDto.getAnhSanPham().getContentType();

            // Kiểm tra định dạng ảnh
            if (contentType == null || !contentType.startsWith("image/")) {
                return false;
            }
            String nameFile = fileUpload.saveFile(chiTietSanPhamDto.getAnhSanPham());

            ChiTietSanPham chiTietSanPham1 = new ChiTietSanPham();
            chiTietSanPham1.setSoLuong(chiTietSanPhamDto.getSoLuong());
            chiTietSanPham1.setQrCode(chiTietSanPhamDto.getQrCode());
            chiTietSanPham1.setDonGia(chiTietSanPhamDto.getDonGia());
            chiTietSanPham1.setSanPham(sanPham);
            chiTietSanPham1.setMau(mauSacRepository.findById(chiTietSanPhamDto.getIdMauSac()).orElse(null));
            chiTietSanPham1.setSize(sizeRepository.findById(chiTietSanPhamDto.getIdSize()).orElse(null));
            chiTietSanPham1.setCoAo(coAoRepository.findById(chiTietSanPhamDto.getIdCoAo()).orElse(null));
            chiTietSanPham1.setTayAo(tayAoRepository.findById(chiTietSanPhamDto.getIdTayAo()).orElse(null));
            chiTietSanPham1.setAnhSanPham(nameFile);
            chiTietSanPham1.setTrangThai(Status.ACTIVE);
            chiTietSanPhamRepository.save(chiTietSanPham1);
        }
        return true;
    }

    @Override
    public boolean updateChiTietSanPham(ChiTietSanPhamDtoEdit chiTietSanPham, Integer idChiTiet, Integer idSanPham) throws IOException {

        ChiTietSanPham chiTietSanPham1 = chiTietSanPhamRepository.findById(idChiTiet).orElse(null);
        if(chiTietSanPham1 == null ) {
            return false;
        }

        if(chiTietSanPhamRepository.existsBySanPhamAndMauAndSizeAndQrCodeExcludeItself(idSanPham, chiTietSanPham.getIdMauSac(),
                chiTietSanPham.getIdSize(), chiTietSanPham.getQrCode(), chiTietSanPham1.getId())) {
            return false;
        }
        chiTietSanPham1.setSoLuong(chiTietSanPham.getSoLuong());
        chiTietSanPham1.setQrCode(chiTietSanPham.getQrCode());
        chiTietSanPham1.setDonGia(chiTietSanPham.getDonGia());
        chiTietSanPham1.setMau(mauSacRepository.findById(chiTietSanPham.getIdMauSac()).orElse(null));
        chiTietSanPham1.setSize(sizeRepository.findById(chiTietSanPham.getIdSize()).orElse(null));
        chiTietSanPham1.setCoAo(coAoRepository.findById(chiTietSanPham.getIdCoAo()).orElse(null));
        chiTietSanPham1.setTayAo(tayAoRepository.findById(chiTietSanPham.getIdTayAo()).orElse(null));
        if(chiTietSanPham.getTrangThai() == 1) {
            chiTietSanPham1.setTrangThai(Status.ACTIVE);
        } else {
            chiTietSanPham1.setTrangThai(Status.INACTIVE);
        }
        if (chiTietSanPham.getAnhSanPham() != null && !chiTietSanPham.getAnhSanPham().isEmpty()) {
            String contentType = chiTietSanPham.getAnhSanPham().getContentType();

            // Kiểm tra định dạng ảnh
            if (contentType == null || !contentType.startsWith("image/")) {
                return false;
            }

            // Lưu tệp nếu định dạng hợp lệ
            String nameFile = fileUpload.saveFile(chiTietSanPham.getAnhSanPham());
            chiTietSanPham1.setAnhSanPham(nameFile);
        }

        chiTietSanPhamRepository.save(chiTietSanPham1);
        return true;
    }

    @Override
    public Page<ChiTietSanPham> getPageChiTietSanPham(Integer idSanPham, Pageable pageable) {
        return chiTietSanPhamRepository.findChiTietBySanPham(idSanPham, pageable);
    }

    @Override
    public boolean changeStatus(Integer idChiTiet) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idChiTiet).orElse(null);
        if(chiTietSanPham != null) {
            if(chiTietSanPham.getSoLuong() <= 0) {
                return false;
            }
            if(chiTietSanPham.getTrangThai() == Status.ACTIVE) {
                chiTietSanPham.setTrangThai(Status.INACTIVE);
            } else {
                chiTietSanPham.setTrangThai(Status.ACTIVE);
            }
            chiTietSanPhamRepository.save(chiTietSanPham);
            return true;
        }
        return false;
    }
}
