package com.example.kristp.repository;

import com.example.kristp.entity.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TayAoRepository  extends JpaRepository<TayAo, Integer> {
}
