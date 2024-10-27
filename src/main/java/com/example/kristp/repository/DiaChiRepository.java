package com.example.kristp.repository;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.DiaChi;
import com.example.kristp.entity.KhachHang;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiaChiRepository  extends JpaRepository<DiaChi, Integer> {

    Page<DiaChi> findAllByTrangThai(Status trangThai, Pageable pageable);
}

