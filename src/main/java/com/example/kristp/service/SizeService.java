package com.example.kristp.service;


import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.Size;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public interface SizeService {
    ArrayList<Size> getAllSize();
    ////////////////////////////////////////// CRUD
    Size getSizeById(int id);

    Size addSize(Size size);

    Size updateSize(Size size , Integer idSize);

    void deleteSize(Integer idSize);

    Size timTheoTenSize(String tenSize);
    Page<Size> getPaginationSize(Integer pageNo);
}
