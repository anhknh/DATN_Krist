package com.example.kristp.service;

import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import org.springframework.data.domain.Page;


import java.util.ArrayList;

public interface MauSacService {
    ArrayList<MauSac> getAllMauSac();
    ////////////////////////////////////////// CRUD
    MauSac getMauSacById(int id);

    MauSac addMauSac(MauSac mauSac);

    MauSac updateMauSac(MauSac mauSac , Integer idMauSac);

    void deleteMauSac(Integer idMauSac);

    MauSac timTheoMaMauSac(String maMauSac);
    Page<MauSac> getPaginationMauSac(Integer pageNo);
}
