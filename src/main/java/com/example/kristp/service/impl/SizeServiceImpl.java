package com.example.kristp.service.impl;

import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import com.example.kristp.repository.SizeRepository;
import com.example.kristp.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
