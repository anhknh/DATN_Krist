package com.example.kristp.service.impl;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.TayAo;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.CoAoRepository;
import com.example.kristp.service.CoAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CoAoServiceImpl implements CoAoService {

    @Autowired
    CoAoRepository coAoRepository;
    @Override
    public ArrayList<CoAo> getAllCoAo() {
        return (ArrayList<CoAo>) coAoRepository.findAll();
    }

    @Override
    public CoAo getCoAoById(int id) {
        return coAoRepository.findById(id).get();
    }

    @Override
    public CoAo addCoAo(CoAo coAo) {
        if(timTheoTenCoAo(coAo.getKieuCoAo()) != null){
            return null ;
        }
        coAo.setTrangThai(Status.ACTIVE);
        return coAoRepository.save(coAo);
    }

    @Override
    public CoAo updateCoAo(CoAo coAo, Integer idCoAo) {
        CoAo tayaotheoten = timTheoTenCoAo(coAo.getKieuCoAo());
        if(tayaotheoten != null && !tayaotheoten.getId().equals(idCoAo)) {
            return null;
        }

        CoAo tayao1 = getCoAoById(idCoAo);
        tayao1.setKieuCoAo(coAo.getKieuCoAo());
        tayao1.setMoTa(coAo.getMoTa());
        tayao1.setTrangThai(coAo.getTrangThai());
        return coAoRepository.save(tayao1);
    }



    @Override
    public void deleteCoAo(Integer idCoAo) {
        CoAo coAo = getCoAoById(idCoAo);
        coAo.setTrangThai(Status.INACTIVE);
        coAoRepository.save(coAo);
    }

    @Override
    public CoAo timTheoTenCoAo(String kieuCoAo) {
        Optional<CoAo> coAo = coAoRepository.timKiemKieuCoAo(kieuCoAo);
        if (coAo.isPresent()) {
            return coAo.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<CoAo> getPaginationCoAo(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return coAoRepository.findAll(pageable);
    }

    @Override
    public Page<CoAo> timTatCaTheoTen(Integer pageNo, String ten) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return coAoRepository.findAllByTenLike(pageable,ten);
    }
}
