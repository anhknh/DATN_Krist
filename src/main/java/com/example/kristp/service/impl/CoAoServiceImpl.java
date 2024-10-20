package com.example.kristp.service.impl;

import com.example.kristp.entity.CoAo;
import com.example.kristp.repository.CoAoRepository;
import com.example.kristp.service.CoAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CoAoServiceImpl implements CoAoService {

    @Autowired
    CoAoRepository coAoRepository;
    @Override
    public ArrayList<CoAo> getAllCoAo() {
        return (ArrayList<CoAo>) coAoRepository.findAll();
    }

    @Override
    public ArrayList<CoAo> getAllCoAoHD() {
        return (ArrayList<CoAo>) coAoRepository.findAllCoAo();
    }

    @Override
    public CoAo getCoAoById(int id) {
        return coAoRepository.findById(id).get();
    }
}
