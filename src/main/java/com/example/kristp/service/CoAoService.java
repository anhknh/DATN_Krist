package com.example.kristp.service;

import com.example.kristp.entity.CoAo;

import java.util.ArrayList;

public interface CoAoService {
    ArrayList<CoAo> getAllCoAo();
    ArrayList<CoAo> getAllCoAoHD();
    CoAo getCoAoById(int id);
}
