package com.example.kristp.service;


import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface TayAoService {
    ArrayList<TayAo> getAllTayAo();
    ArrayList<TayAo> getAllTayAoHD();
    TayAo getTayAoById(int id);

    TayAo addTayAo(TayAo tayAo);

    TayAo updateTayAo(TayAo tayAo , Integer idTayAo);

    void deleteTayAo(Integer idTayAo);

    TayAo timTheoTenTayAo(String kieuTayAo);
    Page<TayAo> getPaginationTayAo(Integer pageNo);
    Page<TayAo> timTatCaTheoTen(Integer pageNo, String ten);
}
