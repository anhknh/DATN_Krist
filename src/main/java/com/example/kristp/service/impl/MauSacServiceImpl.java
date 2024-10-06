package com.example.kristp.service.impl;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.MauSac;
import com.example.kristp.repository.MauSacRepository;
import com.example.kristp.service.MauSacService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    MauSacRepository mauSacRepository;
    @Override
    public ArrayList<MauSac> getAllMauSac() {
            return (ArrayList<MauSac>) mauSacRepository.findAll();
    }

    @Override
    public MauSac getMauSacById(int id) {
        return mauSacRepository.findById(id).get();
    }
}
