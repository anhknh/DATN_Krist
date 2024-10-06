package com.example.kristp.service;

import com.example.kristp.entity.CoAo;

import java.util.ArrayList;

public interface CoAoService {
    ArrayList<CoAo> getAllCoAo();
    CoAo getCoAoById(int id);
}
