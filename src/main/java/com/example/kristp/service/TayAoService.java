package com.example.kristp.service;


import com.example.kristp.entity.TayAo;

import java.util.ArrayList;

public interface TayAoService {
    ArrayList<TayAo> getAllTayAo();
    ArrayList<TayAo> getAllTayAoHD();
    TayAo getTayAoById(int id);
}
