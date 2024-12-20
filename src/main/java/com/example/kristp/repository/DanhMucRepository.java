package com.example.kristp.repository;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    @Query(value = "SELECT dm FROM DanhMuc dm where dm.tenDanhMuc = :ten")
    public Optional<DanhMuc> timKiemTenDanhMuc(@Param("ten") String tenChatLieu);

    @Query(value = "select dm from DanhMuc dm where dm.tenDanhMuc LIKE :ten")
    Page<DanhMuc> findAllByTenLike(Pageable pageable, @Param("ten") String ten);

    @Query("from DanhMuc dm where  dm.trangThai = 1")
    List<DanhMuc> findAllDanhMuc();

    List<DanhMuc> findTop4ByTrangThaiOrderByNgayTaoDesc(Status trangThai);
}
