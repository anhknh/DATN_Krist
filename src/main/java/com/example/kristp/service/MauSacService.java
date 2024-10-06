package com.example.kristp.service;

import com.example.kristp.entity.MauSac;


import java.util.ArrayList;

public interface MauSacService {
    ArrayList<MauSac> getAllMauSac();
    MauSac getMauSacById(int id);
}
