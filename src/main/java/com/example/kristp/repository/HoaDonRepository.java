package com.example.kristp.repository;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    List<HoaDon> findByTrangThai(HoaDonStatus trangThai);

    Integer countByTrangThai(HoaDonStatus trangThai);

}
