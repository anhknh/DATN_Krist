package com.example.kristp.service.impl;

import com.example.kristp.entity.GioHangChiTiet;
import com.example.kristp.repository.GioHangChiTietRepository;
import com.example.kristp.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;


    @Override
    public ArrayList<GioHangChiTiet> getAllGHCT() {
        return (ArrayList<GioHangChiTiet>) gioHangChiTietRepository.findAll();
    }

    @Override
    public List<GioHangChiTiet> getGioHangChiTietByGioHangId(Integer gioHangId) {
        return gioHangChiTietRepository.findByGioHang_Id(gioHangId);
    }

    @Override
    public GioHangChiTiet addGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet updateGioHangChiTiet(GioHangChiTiet gioHangChiTiet) {
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }


    @Override
    public void deleteGioHangChiTiet(Integer id) {
        gioHangChiTietRepository.deleteById(id);
    }
}
