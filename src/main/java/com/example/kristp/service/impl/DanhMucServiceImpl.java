package com.example.kristp.service.impl;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
