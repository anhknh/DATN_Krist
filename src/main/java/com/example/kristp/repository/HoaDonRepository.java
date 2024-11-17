package com.example.kristp.repository;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.enums.HoaDonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    List<HoaDon> findByTrangThai(HoaDonStatus trangThai);

    Integer countByTrangThai(HoaDonStatus trangThai);

    @Query(value = "select hd from HoaDon hd where hd.trangThai = ?1")
    public Page<HoaDon> getPaginationTrangThai(Pageable pageable , HoaDonStatus trangThai);
}
