package com.example.kristp.repository;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoAoRepository extends JpaRepository<CoAo, Integer> {
    @Query(value = "SELECT ca FROM CoAo ca where ca.kieuCoAo = :kieuCoAo")
    Optional<CoAo> timKiemKieuCoAo(@Param("kieuCoAo") String kieuCoAo);
}
