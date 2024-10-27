package com.example.kristp.service;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface CoAoService {
    ArrayList<CoAo> getAllCoAo();
    ArrayList<CoAo> getAllCoAoHD();
    CoAo getCoAoById(int id);
    CoAo addCoAo(CoAo coAo);

    CoAo updateCoAo(CoAo coAo , Integer idCoAo);

    void deleteCoAo(Integer idCoAo);

    CoAo timTheoTenCoAo(String kieuCoAo);
    Page<CoAo> getPaginationCoAo(Integer pageNo);
    Page<CoAo> timTatCaTheoTen(Integer pageNo, String ten);
}
