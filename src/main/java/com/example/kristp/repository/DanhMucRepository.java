package com.example.kristp.repository;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    @Query(value = "SELECT dm.tenDanhMuc FROM DanhMuc dm where dm.tenDanhMuc = :ten")
    public Optional<DanhMuc> timKiemTenDanhMuc(@Param("ten") String tenChatLieu);
}
