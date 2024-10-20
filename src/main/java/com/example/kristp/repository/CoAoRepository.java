package com.example.kristp.repository;

import com.example.kristp.entity.CoAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoAoRepository extends JpaRepository<CoAo, Integer> {

    @Query("from CoAo co where co.trangThai = 1")
    List<CoAo> findAllCoAo();
}
