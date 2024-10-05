package com.example.kristp.repository;

import com.example.kristp.entity.CoAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoAoRepository extends JpaRepository<CoAo, Integer> {
}
