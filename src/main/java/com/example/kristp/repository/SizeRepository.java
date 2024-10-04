package com.example.kristp.repository;
import com.example.kristp.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SizeRepository  extends JpaRepository<Size,Integer>{
}
