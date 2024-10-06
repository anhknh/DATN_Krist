package com.example.kristp.service;


import com.example.kristp.entity.Size;

import java.util.ArrayList;

public interface SizeService {
    ArrayList<Size> getAllSize();
    Size getSizeById(int id);
}
