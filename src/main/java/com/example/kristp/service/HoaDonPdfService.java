package com.example.kristp.service;
import com.example.kristp.entity.HoaDon;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface HoaDonPdfService {
      ResponseEntity<InputStreamResource> inHoaDon(Integer idOrder);
      List<HoaDon> findAllHoaDon();

}
