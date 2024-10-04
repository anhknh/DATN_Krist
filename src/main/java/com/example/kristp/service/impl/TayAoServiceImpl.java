package com.example.kristp.service.impl;

import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import com.example.kristp.repository.TayAoRepository;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
