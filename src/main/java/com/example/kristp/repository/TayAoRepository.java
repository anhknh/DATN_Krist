package com.example.kristp.repository;

import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TayAoRepository  extends JpaRepository<TayAo, Integer> {
    @Query(value = "SELECT ta FROM TayAo ta where ta.kieuTayAo = :kieuTayAo")
    Optional<TayAo> timKiemKieuTayAo(@Param("kieuTayAo") String kieuTayAo);

    @Query(value = "select ta from TayAo ta where ta.kieuTayAo LIKE :ten")
    Page<TayAo> findAllByTenLike(Pageable pageable, @Param("ten") String ten);
}
