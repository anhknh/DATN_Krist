package com.example.kristp.service.impl;


import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.TayAoRepository;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TayAoServiceImpl implements TayAoService {
    @Autowired
    TayAoRepository tayAoRepository;
    @Override
    public ArrayList<TayAo> getAllTayAo() {
        return (ArrayList<TayAo>) tayAoRepository.findAll();
    }

    @Override
    public TayAo getTayAoById(int id) {
        return tayAoRepository.findById(id).get();
    }

    @Override
    public TayAo addTayAo(TayAo tayAo) {
        if(timTheoTenTayAo(tayAo.getKieuTayAo()) != null){
            return null ;
        }
       tayAo.setTrangThai(Status.ACTIVE);
        return tayAoRepository.save(tayAo);
    }

    @Override
    public TayAo updateTayAo(TayAo tayAo, Integer idTayAo) {

        TayAo tayaotheoten = timTheoTenTayAo(tayAo.getKieuTayAo());
        if(tayaotheoten != null && !tayaotheoten.getId().equals(idTayAo)) {
            return null;
        }

        TayAo tayao1 = getTayAoById(idTayAo);
        tayao1.setKieuTayAo(tayAo.getKieuTayAo());
        tayao1.setMoTa(tayAo.getMoTa());
        tayao1.setTrangThai(tayAo.getTrangThai());
        return tayAoRepository.save(tayao1);
    }

    @Override
    public void deleteTayAo(Integer idTayAo) {
       TayAo tayAo = getTayAoById(idTayAo);
        tayAo.setTrangThai(Status.INACTIVE);
        tayAoRepository.save(tayAo);
    }

    @Override
    public TayAo timTheoTenTayAo(String kieuTayAo) {
        Optional<TayAo> tayAo = tayAoRepository.timKiemKieuTayAo(kieuTayAo);
        if (tayAo.isPresent()) {
            return tayAo.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<TayAo> getPaginationTayAo(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return tayAoRepository.findAll(pageable);
    }
}
