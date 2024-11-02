package com.example.kristp.service;
import com.example.kristp.entity.HoaDon;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface HoaDonPdfService {
      void inHoaDon(Integer idOrder);
      List<HoaDon> findAllHoaDon();

}
