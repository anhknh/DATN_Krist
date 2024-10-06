package com.example.kristp.repository;
;
import com.example.kristp.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface MauSacRepository  extends JpaRepository<MauSac, Integer> {
}
