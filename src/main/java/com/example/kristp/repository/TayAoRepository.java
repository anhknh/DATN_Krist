package com.example.kristp.repository;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TayAoRepository  extends JpaRepository<TayAo, Integer> {

    @Query("from TayAo to where  to.trangThai = 1")
    List<TayAo> findAllTayAo();
}
