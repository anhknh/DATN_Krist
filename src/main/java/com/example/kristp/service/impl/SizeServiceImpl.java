package com.example.kristp.service.impl;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.SizeRepository;
import com.example.kristp.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public ArrayList<Size> getAllSize() {
        return (ArrayList<Size>) sizeRepository.findAll();
    }

    @Override
    public Size getSizeById(int id) {
        return sizeRepository.findById(id).get();
    }

    @Override
    public Size addSize(Size size) {
        if(timTheoTenSize(size.getTenSize()) != null){
            return null ;
        }
        size.setTrangThai(Status.ACTIVE);
        return sizeRepository.save(size);
    }

    @Override
    public Size updateSize(Size size, Integer idSize) {

        Size sizetheoten = timTheoTenSize(size.getTenSize());
        if(sizetheoten != null && !sizetheoten.getId().equals(idSize)) {
            return null;
        }

        Size size1 = getSizeById(idSize);
        size1.setTenSize(size.getTenSize());
        size1.setMoTa(size.getMoTa());
        size1.setTrangThai(size.getTrangThai());
        return sizeRepository.save(size1);
    }

    @Override
    public void deleteSize(Integer idSize) {
        Size size = getSizeById(idSize);
        size.setTrangThai(Status.INACTIVE);
        sizeRepository.save(size);
    }

    @Override
    public Size timTheoTenSize(String tenSize) {
        Optional<Size> size = sizeRepository.timKiemTenSize(tenSize);
        if (size.isPresent()) {
            return size.get();
        } else {
            return null;
        }
}
    //    Phân trang cho phần quản lý size thay cho get all
    @Override
    public Page<Size> getPaginationSize(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return sizeRepository.findAll(pageable);
    }

    @Override
    public Page<Size> timTatCaTheoTen(Integer pageNo, String ten) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return sizeRepository.findAllByTenLike(pageable,ten);
    }
}
