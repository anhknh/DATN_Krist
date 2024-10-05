package com.example.kristp.service.impl;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    DanhMucRepository danhMucRepository;
    @Override
    public ArrayList<DanhMuc> getAllDanhMuc() {
        return (ArrayList<DanhMuc>) danhMucRepository.findAll();
    }

    @Override
    public DanhMuc getDanhmucById(int id) {
        return danhMucRepository.findById(id).get();
    }

    @Override
    public DanhMuc addDanhMuc(DanhMuc danhMuc) {
        if(timTheoTen(danhMuc.getTenDanhMuc()) != null){
            return null ;
        }
        danhMuc.setTrangThai(Status.ACTIVE);
        return danhMucRepository.save(danhMuc);
    }

    @Override
    public DanhMuc updateDanhMuc(DanhMuc danhMuc, Integer idDanhMuc) {
        if(timTheoTen(danhMuc.getTenDanhMuc()) != null){
            return null ;
        }
        DanhMuc danhMuc1 = getDanhmucById(idDanhMuc);
        danhMuc1.setTenDanhMuc(danhMuc.getTenDanhMuc());
        danhMuc1.setMoTa(danhMuc.getMoTa());
        danhMuc1.setTrangThai(danhMuc.getTrangThai());
        return danhMucRepository.save(danhMuc1);
    }

    @Override
    public void deleteDanhMuc(Integer idDanhMuc) {
        danhMucRepository.deleteById(idDanhMuc);
    }

    @Override
    public DanhMuc timTheoTen(String tenDanhMuc) {
        Optional<DanhMuc> danhMuc = danhMucRepository.timKiemTenDanhMuc(tenDanhMuc);
        if (danhMuc.isPresent()) {
            return danhMuc.get();
        } else {
            return null;
        }
    }
}
